def find_password_rank(file_path, target_password):
    with open(file_path, 'r', encoding='utf-8') as f:
        for rank, line in enumerate(f, start=1):
            if line.strip() == target_password:
                return rank
    return None 

# Example usage
file_path = "passwords.txt" 
password_to_find = "shamrock"  

rank = find_password_rank(file_path, password_to_find)
if rank:
    print(f"The password '{password_to_find}' is ranked {rank}.")
else:
    print("Password not found in the list.")