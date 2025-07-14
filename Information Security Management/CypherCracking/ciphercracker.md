### Stage 0 (substituion cipher)

This part of the challenge was fairly straight forward. First I counted the number of appearances of each letter in the entire text. Then I found the frequency order of letter of the english alphabet and simply made the substitutions (for example if "z" appeared the most in the text, then I would substitute it with "e"). This worked fairly well but still the result wasn't perfect, so I just started to locate words that had slight mistakes (1 or 2 letters where wrong but I still knew the word) so I manually made a few extra substitutions (there were quite a few extra substitutions that needed to be made but it was fairly easy because most wrods were almost complete from the first step).

At the end I was able to get all the substitutions right:
true_frequency = ['e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'l', 'd', 'u', 'm', 'y', 'w', 'c', 'g', 'b', 'f', 'p','k', 'v', 'z', 'j', 'x', 'q'] and derived this flag: flg_aswvgyz2abozj2fjgayl67y=


### Stage 1 (one-time-pad)

For this challenge the saving grace was the fact that the same encryption key was used on all the texts, so using crib dragging could theoreticaly get the job done. So for starters I created a list of pretty common english words crib_words = [
    "the", "and", "that", "have", "for",
    "with", "this", "from", "which", "they",
    "will", "would", "there", "their", "what",
    "about", "when", "your", "could", "some"
] and used crib dragging on pairs of ciphertexts (ciphertext1 and ciphertext2). Whenever I found a ciphertext (let's say ciphertext1) that did actually have one of these words and subsequently discovered some info about ciphertext2, I tried to guess some of the words to hopefully get more info (for example between the 2 first ciphertexts if you use "with" as the crib word on the xor of text_2 and text_1 you get "y ne", well I tested a few words that started with ne and need (and later on needs) gave me a result). Repeating that plenty of times I was actually able to decypher almost all the texts except for the 2 largest ones, however that wasn't really needed considering that once I found the real text of the ciphertext 2 I simply found the key used to encrypt it (or rather the part of the original key) and used the same key to decrypt the challenge and get the final flag: flg_aswvgyz2ahwbc5pltkhhcxi=


### Stage_0 questions
* The decryption process was made possible because simple substitution cyphers are fairly weak and simple to break.
* Well as stated above, substitution cyphers are most certaintly not a good choise if you want you message to be truly unbreakable (or really hard to break), however substitution ciphers like the Vigenère Cipher provide with a more complex algorithm and thus are way harder to crack than just simple 1 to 1 sumbsitution.


### Stage_1 questions
* Well as far as I undestand, the problem with the encryption here was that the same key was used on all the texts, or rather each text used a part of the same key, starting from the beginning of the key and ending at the maximum depending on the size of the texts, thus creating a situation where just by using crib dragging you can actually read a lot of the texts. So despite one pad actually being unbreakable, due to key reuse we were actually able to break it.