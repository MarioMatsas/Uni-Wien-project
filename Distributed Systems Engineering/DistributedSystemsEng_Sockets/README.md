# IMPORTANT
1. For the thread impleemntation in task 4 I have decided to not close the sockets, because it was easier for me to perform tests that way.
2. Rather than a total of 1000 iterations, I perform 1000 iterations per blob but that shouldn't be a problem because the comparisons still show the expected results.
3. In Task_4 both servers have 2 implementations, thread pool and no thread pool, so use comments to test both of them. 
4. To run the code compile all the files and run the server first before running the client.
   
# OBSERVATIONS
1. UDP is faster than TCP and by quite a bit actually.
2. For the UDP client and server I had to create a work around (a poor excuse of a fragmentation) in order to combat the fact that I can't send datagrams with a blobsize of 128KB.
3. Thread pools where slightly faster that no thread pool.
4. Using more threads actually made the average slightly slower, probably because of the added overhead.