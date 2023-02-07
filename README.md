# Huffman_Encoding-Decoding
Encodes and decodes given text using Huffman Coding.
Project was made during 3rd term of my CS studies - Algorithms and Data Structures - Warsaw University of Technology.

> 'Huffman.java' is a main code - it's launches other stuff.

> 'HuffmanTest.java' contains two important methods - compress & decompress - you can start using them right after copying this repository.

> Project compresses and decompresses files located in the '/data' folder.

Algorithm works on a correctly named files:
  1. toCompress.txt - should be containing text you want to compress (Note: Text should be in UTF-8).
  1. tree.txt - contains a Huffman Tree of codes for, a compressed text.
  1. compressed.bin - contains compressed text by codes in 'tree.txt'.
  1. decompressed.txt - should be containing decompressed text file (for decompression you need 'compressed.bin' and 'tree.txt')

*Note: You can change names of the necessary files at the beggining of 'Huffman.java'.*
