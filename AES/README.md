AES
===
Implementation of Advanced Encryption Standard. This repo is an extansion of [AES](https://github.com/dhuertas/AES)

# Example

Compile the source code (e.g. using GCC): 

`gcc gmult.c aes.c main.c -o aes`

And run:

```bash
./aes
Plaintext input_message:
00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff
Ciphered input_message:
8e a2 b7 ca 51 67 45 bf ea fc 49 90 4b 49 60 89
Original input_message (after inv cipher):
00 11 22 33 44 55 66 77 88 99 aa bb cc dd ee ff
```
