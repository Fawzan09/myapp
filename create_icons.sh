#!/bin/bash

# Create placeholder icon files for different densities
# This script creates simple placeholder icons for the app

echo "🎨 Creating placeholder app icons..."

# Create placeholder icon content (simple colored square as placeholder)
cat > /tmp/create_icon.py << 'EOF'
from PIL import Image, ImageDraw
import sys

def create_icon(size, output_path):
    # Create image with gradient-like background
    img = Image.new('RGBA', (size, size), (33, 150, 243, 255))  # Material Blue
    draw = ImageDraw.Draw(img)
    
    # Draw simple AI-like symbol (circle with dot)
    margin = size // 4
    circle_size = size - 2 * margin
    
    # Outer circle (white)
    draw.ellipse([margin, margin, margin + circle_size, margin + circle_size], 
                fill=(255, 255, 255, 255), outline=None)
    
    # Inner circle (accent color)
    inner_margin = margin + circle_size // 4
    inner_size = circle_size // 2
    draw.ellipse([inner_margin, inner_margin, inner_margin + inner_size, inner_margin + inner_size], 
                fill=(255, 215, 0, 255), outline=None)  # Gold color
    
    img.save(output_path)
    print(f"Created {output_path} ({size}x{size})")

if __name__ == "__main__":
    size = int(sys.argv[1])
    output_path = sys.argv[2]
    create_icon(size, output_path)
EOF

# Check if Python and PIL are available
if command -v python3 >/dev/null 2>&1; then
    # Try to install PIL if not available
    python3 -c "from PIL import Image" 2>/dev/null || {
        echo "📦 Installing Pillow for icon generation..."
        pip3 install Pillow 2>/dev/null || pip install Pillow 2>/dev/null || {
            echo "⚠️  Could not install Pillow. Creating simple placeholder files..."
            # Create empty placeholder files
            touch /workspaces/myapp/app/src/main/res/mipmap-mdpi/ic_launcher.png
            touch /workspaces/myapp/app/src/main/res/mipmap-hdpi/ic_launcher.png
            touch /workspaces/myapp/app/src/main/res/mipmap-xhdpi/ic_launcher.png
            touch /workspaces/myapp/app/src/main/res/mipmap-xxhdpi/ic_launcher.png
            touch /workspaces/myapp/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
            # Copy for round versions
            touch /workspaces/myapp/app/src/main/res/mipmap-mdpi/ic_launcher_round.png
            touch /workspaces/myapp/app/src/main/res/mipmap-hdpi/ic_launcher_round.png
            touch /workspaces/myapp/app/src/main/res/mipmap-xhdpi/ic_launcher_round.png
            touch /workspaces/myapp/app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png
            touch /workspaces/myapp/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png
            echo "✅ Created placeholder icon files"
            exit 0
        }
    }
    
    # Generate icons with different sizes
    python3 /tmp/create_icon.py 48 /workspaces/myapp/app/src/main/res/mipmap-mdpi/ic_launcher.png
    python3 /tmp/create_icon.py 72 /workspaces/myapp/app/src/main/res/mipmap-hdpi/ic_launcher.png
    python3 /tmp/create_icon.py 96 /workspaces/myapp/app/src/main/res/mipmap-xhdpi/ic_launcher.png
    python3 /tmp/create_icon.py 144 /workspaces/myapp/app/src/main/res/mipmap-xxhdpi/ic_launcher.png
    python3 /tmp/create_icon.py 192 /workspaces/myapp/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
    
    # Copy for round versions
    cp /workspaces/myapp/app/src/main/res/mipmap-mdpi/ic_launcher.png /workspaces/myapp/app/src/main/res/mipmap-mdpi/ic_launcher_round.png
    cp /workspaces/myapp/app/src/main/res/mipmap-hdpi/ic_launcher.png /workspaces/myapp/app/src/main/res/mipmap-hdpi/ic_launcher_round.png
    cp /workspaces/myapp/app/src/main/res/mipmap-xhdpi/ic_launcher.png /workspaces/myapp/app/src/main/res/mipmap-xhdpi/ic_launcher_round.png
    cp /workspaces/myapp/app/src/main/res/mipmap-xxhdpi/ic_launcher.png /workspaces/myapp/app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png
    cp /workspaces/myapp/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png /workspaces/myapp/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png
    
    echo "✅ Created all icon variants successfully!"
    
else
    echo "⚠️  Python not available. Creating empty placeholder files..."
    # Create empty placeholder files
    touch /workspaces/myapp/app/src/main/res/mipmap-mdpi/ic_launcher.png
    touch /workspaces/myapp/app/src/main/res/mipmap-hdpi/ic_launcher.png
    touch /workspaces/myapp/app/src/main/res/mipmap-xhdpi/ic_launcher.png
    touch /workspaces/myapp/app/src/main/res/mipmap-xxhdpi/ic_launcher.png
    touch /workspaces/myapp/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
    touch /workspaces/myapp/app/src/main/res/mipmap-mdpi/ic_launcher_round.png
    touch /workspaces/myapp/app/src/main/res/mipmap-hdpi/ic_launcher_round.png
    touch /workspaces/myapp/app/src/main/res/mipmap-xhdpi/ic_launcher_round.png
    touch /workspaces/myapp/app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png
    touch /workspaces/myapp/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png
    echo "✅ Created placeholder icon files"
fi

# Clean up
rm -f /tmp/create_icon.py

echo ""
echo "🎯 Icon generation complete!"
echo "📝 Note: For production, replace with professionally designed icons"
