def xor_bytes(a, b):
    return bytes([x ^ y for x, y in zip(a, b)])

# Find a key and try to decypher the challenge text
plaintext = "i help people with problems.".encode()
ciphertext_hex = "5d1450000d434448550b165e0343470b4c0a1745470b5354525d1748"
ciphertext = bytes.fromhex(ciphertext_hex)

key = xor_bytes(ciphertext, plaintext)

challenge_hex = "52585f3a0040134e571d1c00070b47005b574759410f595054480d5b"
challenge = bytes.fromhex(challenge_hex)

# Repeat key if necessary (cut to same length)
full_key = key[:len(challenge)]

decrypted = xor_bytes(challenge, full_key)
print(decrypted.decode())