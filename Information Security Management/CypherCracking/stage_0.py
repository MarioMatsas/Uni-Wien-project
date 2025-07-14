import sys


def count_letter_frequencies():
    letter_freq = {} 
    
    with open("stage_0.txt", 'r', encoding='utf-8') as file:
        # Skip the first line
        next(file)  
        for line in file:
            for char in line:
                # We only care about alphabetical characters
                if char.isalpha():  
                    char_lower = char.lower()
                    if char_lower in letter_freq:
                        letter_freq[char_lower] += 1
                    else:
                        letter_freq[char_lower] = 1
    
    return sorted(letter_freq.items(), key=lambda x: -x[1])

frequencies = count_letter_frequencies()
# Usually, from most frequent to least frequent
true_frequency = ['e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'l', 'd', 'u', 'm', 'y', 'w', 'c', 'g', 'b', 'f', 'p','k', 'v', 'z', 'j', 'x', 'q']
for letter in true_frequency:
    print(letter)

replacements = [[frequencies[i][0], true_frequency[i]] for i in range(len(frequencies))]

print(replacements) #16 -> c, -> k -> 26, f->19, b->p, r->e u->y, b->u, a->e
#sys.exit()
#sys.exit()
text = ""
with open("stage_0.txt", 'r', encoding='utf-8') as file:
    # Skip the first line
    next(file)  
    for line in file:
        for char in line:
            if char.isalpha():  
                char_lower = char.lower()
                for rep in replacements:
                    if rep[0] == char_lower:
                        text += rep[1]
                        break
            else:
                text += char
print(text)
