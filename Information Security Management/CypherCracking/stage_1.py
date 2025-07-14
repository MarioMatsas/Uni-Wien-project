# Load all the cipher texts
messages = []
with open("stage_1.txt", 'r') as f:
    lines = f.readlines()
    for line in lines[1:]:
        if not line.strip(): break
        else:
            messages.append(line.strip())

for message in messages:
    print(message)

# Choose 2 ciphertexts to use for the crib dragging
m1 = messages[1]
m2 = messages[0]

# Crib words found to be fairly common in English text
crib_words = [
    "the", "and", "that", "have", "for",
    "with", "this", "from", "which", "they",
    "will", "would", "there", "their", "what",
    "about", "when", "your", "could", "some"
]

# Convert hex strings to bytes
m1_bytes = bytes.fromhex(m1)
m2_bytes = bytes.fromhex(m2)

# XOR the two ciphertexts
xored = bytes(a ^ b for a, b in zip(m1_bytes, m2_bytes))

"""for word in crib_words:
    print("\nCURRENTLY USING: "+word)

    # Convert the word to bytes
    word_bytes = word.encode('ascii')
    word_len = len(word_bytes)

    # Slide the word through the XOR result
    for i in range(len(xored) - word_len + 1):
        # XOR the word with the current position in the XOR result
        possible_plaintext = bytes(xored[i+j] ^ word_bytes[j] for j in range(word_len))
        print(f"Potision: {i} text: {possible_plaintext.decode('ascii')}")
        let's count to three. you can do that, can't you
        """

word = "there's a saying in england: where there's smoke, there's fire.we all "
word = "with"
print("\nCURRENTLY USING: "+word)

# Convert the word to bytes
word_bytes = word.encode('ascii')
word_len = len(word_bytes)

# Slide the word through the XOR result
for i in range(len(xored) - word_len + 1):
    # XOR the word with the current position in the XOR result
    possible_plaintext = bytes(xored[i+j] ^ word_bytes[j] for j in range(word_len))
    print(f"Potision: {i} text: {possible_plaintext.decode('ascii')}")
