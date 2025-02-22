#!/bin/bash

set -e  # Exit the script immediately if an error occurs

# Name of the ZIP archive
ARCHIVE_NAME="source_code.zip"

# Declare INCLUDE_ITEMS as an array
INCLUDE_ITEMS=("src" "resources/input" "default.env")

# Determine the project root directory
PROJECT_ROOT=$(git rev-parse --show-toplevel 2>/dev/null)

if [ -z "$PROJECT_ROOT" ]; then
    echo "Error: This directory does not appear to be a Git repository."
    exit 1
fi

# Change to the project root directory
cd "$PROJECT_ROOT"

# Create the ZIP archive using git archive
git archive --format=zip --output="$ARCHIVE_NAME" HEAD "${INCLUDE_ITEMS[@]}" || {
    echo "Error creating the ZIP archive with git archive.";
    exit 1;
}

# Print the full path of the created archive
echo "Backup created successfully: $(realpath "$ARCHIVE_NAME")"
