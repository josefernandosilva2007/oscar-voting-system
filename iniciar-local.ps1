param([switch]$MostrarSenhaAdmin, [switch]$ConfigurarTMDB)

$ErrorActionPreference = 'Stop'
$dir = Join-Path $env:LOCALAPPDATA 'OscarComOsGurizes'
[void][IO.Directory]::CreateDirectory($dir)
$adminFile = Join-Path $dir 'admin.xml'
$tokenFile = Join-Path $dir 'tmdb.xml'

function New-LocalSecret {
    $bytes = New-Object byte[] 32
    $rng = [Security.Cryptography.RandomNumberGenerator]::Create()
    try { $rng.GetBytes($bytes) } finally { $rng.Dispose() }
    [Convert]::ToBase64String($bytes)
}

if (!(Test-Path -LiteralPath $adminFile)) {
    $password = ConvertTo-SecureString (New-LocalSecret) -AsPlainText -Force
    [PSCredential]::new('admin-local', $password) | Export-Clixml -LiteralPath $adminFile
}
$admin = Import-Clixml -LiteralPath $adminFile
if ($MostrarSenhaAdmin) {
    $admin.GetNetworkCredential().Password
    return
}
if ($ConfigurarTMDB) {
    $token = Read-Host 'Cole o API Read Access Token do TMDB e pressione Enter (Ctrl+V usa a area de transferencia)' -AsSecureString
    $text = [PSCredential]::new('TMDB', $token).GetNetworkCredential().Password
    # Some Windows consoles deliver the Ctrl+V keystroke itself to a masked prompt.
    if ($text -eq [string][char]22) { $text = Get-Clipboard -Raw }
    if (!$text -or $text.Trim() -cnotmatch '\A[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+\z') {
        throw 'Token invalido. Copie o API Read Access Token completo em themoviedb.org/settings/api e tente novamente.'
    }
    $token = ConvertTo-SecureString $text.Trim() -AsPlainText -Force
    [PSCredential]::new('TMDB', $token) | Export-Clixml -LiteralPath $tokenFile
    Write-Host 'Token salvo. Reinicie o backend para aplicar.'
    return
}

if (Get-NetTCPConnection -State Listen -LocalPort 8080 -ErrorAction SilentlyContinue) {
    throw 'A porta 8080 ja esta em uso. Se o backend local ja esta rodando, use o terminal dele para encerrar com Ctrl+C.'
}
Push-Location $PSScriptRoot
$savedEnv = @{}
try {
    $settings = @{
        OCG_LOCAL_ADMIN_PASSWORD = $admin.GetNetworkCredential().Password
        OSCAR_AUTH_SECRET = New-LocalSecret
        OSCAR_AUTH_ADMIN_USERNAME = $admin.UserName
        SPRING_PROFILES_ACTIVE = 'local'
    }
    if (!$env:TMDB_READ_ACCESS_TOKEN -and (Test-Path -LiteralPath $tokenFile)) {
        $settings.TMDB_READ_ACCESS_TOKEN = (Import-Clixml -LiteralPath $tokenFile).GetNetworkCredential().Password
    }
    foreach ($key in $settings.Keys) {
        $savedEnv[$key] = [Environment]::GetEnvironmentVariable($key)
        [Environment]::SetEnvironmentVariable($key, $settings[$key])
    }
    Write-Host 'API local: http://127.0.0.1:8080/nominations | Usuario: admin-local | Ctrl+C para parar'
    & .\mvnw.cmd -Plocal-h2 spring-boot:run
    if ($LASTEXITCODE) { throw "A API encerrou com codigo $LASTEXITCODE." }
} finally {
    foreach ($key in $savedEnv.Keys) {
        [Environment]::SetEnvironmentVariable($key, $savedEnv[$key])
    }
    Pop-Location
}
