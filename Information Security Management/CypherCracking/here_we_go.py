def xor_bytes(byte1, byte2):
    """XOR two byte arrays of the same length."""
    return bytes([b1 ^ b2 for b1, b2 in zip(byte1, byte2)])

def crib_drag(ciphertext1, ciphertext2, crib):
    """Perform crib dragging to find the position where the crib matches."""
    
    # Convert the crib word to bytes (assuming it's in ASCII)
    crib_bytes = crib.encode('utf-8')
    
    # Prepare an empty list to store match positions
    matches = []
    
    # Length of the ciphertexts
    len1, len2 = len(ciphertext1), len(ciphertext2)
    
    # Ensure ciphertexts are the same length, if they are not, truncate to the smaller length
    min_len = min(len1, len2)
    
    # Loop through all possible starting positions for the crib in ciphertext1
    for start in range(min_len - len(crib_bytes) + 1):
        # XOR the portion of ciphertext1 with ciphertext2 at the same starting point
        sub_ciphertext1 = ciphertext1[start:start + len(crib_bytes)]
        sub_ciphertext2 = ciphertext2[start:start + len(crib_bytes)]
        
        # XOR the two portions
        xored = xor_bytes(sub_ciphertext1, sub_ciphertext2)
        
        # Check if the crib appears in the XORed result
        if crib_bytes in xored:
            matches.append(start)
    
    return matches

# Example usage
ciphertext1 = bytes.fromhex('405c59114640445245171212071010005906175446445d51444401085f5a5314155714445a55145200501009521610120f110956464d165257165d1703544b4a')
ciphertext2 = bytes.fromhex('405c5911415f054b44440e530807100c5d0345594c445a515b5c01021659511a')

# The crib word you're looking for (the plaintext word)
crib = "that's"

# Run the crib dragging function
matches = crib_drag(ciphertext1, ciphertext2, crib)

# Display the matches found
if matches:
    print(f"Matches found at positions: {matches}")
else:
    print("No matches found.")
