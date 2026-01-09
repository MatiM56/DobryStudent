import os
import re

SOURCE_DIR = r"src"
OUTPUT_FILE = r"dokumentacja/dokumentacja.txt"

def extract_docs(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    # Regex to capture Javadoc comments and the immediate following signature
    # Group 1: Javadoc content
    # Group 2: Signature (up to { or ;)
    # We use non-greedy matching for the comment part
    pattern = re.compile(r'/\*\*(.*?)\*/\s*([\w\s<>,\[\]]+\s*[;{])', re.DOTALL)
    
    matches = pattern.findall(content)
    
    docs = []
    if matches:
        docs.append(f"=== PLIK: {file_path} ===")
        for comment, signature in matches:
            # Clean up the comment (remove * and leading spaces)
            lines = []
            for line in comment.split('\n'):
                stripped = line.strip()
                if stripped.startswith('*'):
                    stripped = stripped[1:].strip()
                if stripped:
                    lines.append(stripped)
            clean_comment = '\n'.join(lines)
            
            # Clean up signature
            clean_signature = signature.strip().rstrip('{').rstrip(';')
            # remove newlines from signature to keep it one line if possible
            clean_signature = ' '.join(clean_signature.split())
            
            docs.append(f"METODA/KLASA: {clean_signature}")
            docs.append(f"OPIS:\n{clean_comment}")
            docs.append("-" * 40)
        docs.append("\n\n")
    
    return "\n".join(docs)

def main():
    if not os.path.exists('dokumentacja'):
        os.makedirs('dokumentacja')
        
    all_docs = []
    
    # Header
    all_docs.append("DOKUMENTACJA PROJEKTU")
    all_docs.append("=====================\n")

    for root, dirs, files in os.walk(SOURCE_DIR):
        for file in files:
            if file.endswith(".java"):
                path = os.path.join(root, file)
                file_docs = extract_docs(path)
                if file_docs:
                    all_docs.append(file_docs)
    
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        f.write("\n".join(all_docs))
    
    print(f"Generated text documentation at: {os.path.abspath(OUTPUT_FILE)}")

if __name__ == "__main__":
    main()
