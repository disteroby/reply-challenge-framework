# Set the name of the ZIP archive
$ARCHIVE_NAME = "source_code.zip"

# Declare items to include in the archive
$INCLUDE_ITEMS = @("src", "resources/input", "default.env")

# Determine the project root directory
$PROJECT_ROOT = git rev-parse --show-toplevel 2>$null

if (-not $PROJECT_ROOT) {
    Write-Host "Error: This directory does not appear to be a Git repository."
    exit 1
}

# Change to the project root directory
Set-Location -Path $PROJECT_ROOT

# Create the ZIP archive using git archive
git archive --format=zip --output=$ARCHIVE_NAME HEAD $INCLUDE_ITEMS

if ($?) {
    # Print the full path of the created archive
    Write-Host "Backup created successfully: $(Resolve-Path $ARCHIVE_NAME)"
} else {
    Write-Host "Error creating the ZIP archive with git archive."
    exit 1
}
