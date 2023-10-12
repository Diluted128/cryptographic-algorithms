/*
 * Advanced Encryption Standard
 * @author Dani Huertas
 * @email huertas.dani@gmail.com
 *
 * Based on the document FIPS PUB 197
 */
#include <stdio.h>
#include "aes.h"

size_t number_of_words = 8;
size_t number_of_rounds = 14;

/* 256 bit key */
uint8_t key[] = {
        0x00, 0x01, 0x02, 0x03,
        0x04, 0x05, 0x06, 0x07,
        0x08, 0x09, 0x0a, 0x0b,
        0x0c, 0x0d, 0x0e, 0x0f,
        0x10, 0x11, 0x12, 0x13,
        0x14, 0x15, 0x16, 0x17,
        0x18, 0x19, 0x1a, 0x1b,
        0x1c, 0x1d, 0x1e, 0x1f};

uint8_t input_message[] = {
        0x63, 0x7A, 0x65, 0x73,
        0x63,0x20, 0x61, 0x6C,
        0x61,0x20,0x63, 0x6F,
        0x20, 0x72,0x6F,0x62,
        0x7A, 0x69, 0x7A,0x20,
        0x6A, 0x75, 0x74, 0x72,
        0x6F,0x20, 0x70, 0x6F,
        0x20,0x70,0x6F, 0x6C};

void encrypt_blocks(uint8_t *encrypted_blocks, uint8_t number_of_blocks, uint8_t *expanded_key){
    uint8_t *single_block = malloc(16);
    uint8_t encrypted_message[16];

    for (uint8_t i=0; i<number_of_blocks; i++) {
        for (int8_t j = 0; j < 16; ++j) {
            single_block[j] = input_message[i * number_of_blocks + j];
        }
        aes_cipher(single_block, encrypted_message, expanded_key);

        for (uint8_t k = 0; k < 16; ++k) {
            encrypted_blocks[i * number_of_blocks + k] = encrypted_message[k];
        }
    }
}

void decrypt_blocks(uint8_t *decrypted_blocks, const uint8_t *encrypted_blocks, uint8_t number_of_blocks, uint8_t *expanded_key){
    uint8_t *single_block = malloc(16);
    uint8_t decrypted_block[16];

    for (uint8_t i=0; i<number_of_blocks; i++) {
        for (int8_t j = 0; j < 16; ++j) {
            single_block[j] = encrypted_blocks[i * number_of_blocks + j];
        }
        aes_inv_cipher(single_block, decrypted_block, expanded_key);

        for (uint8_t k = 0; k < 16; ++k) {
            decrypted_blocks[i * number_of_blocks + k] = decrypted_block[k];
        }
    }
}

int main() {

    int64_t input_message_size = sizeof(input_message);
    uint8_t number_of_blocks = input_message_size / 16;

    uint8_t *encrypted_blocks = malloc(number_of_blocks * 16);
    uint8_t *decrypted_blocks = malloc(number_of_blocks * 16);

    uint8_t *expanded_key = malloc(number_of_words * (number_of_rounds + 1) * 4);

	aes_key_expansion(key, expanded_key);

    printf("Plaintext input_message:\n");
    for (int j = 0; j < number_of_blocks; ++j) {
        for (int i = 0; i < 16; i++) {
            printf("%02x ", input_message[j * number_of_blocks + i]);
        }
        printf("\n");
    }

    printf("\n");

    encrypt_blocks(encrypted_blocks, number_of_blocks, expanded_key);

	printf("Ciphered input_message:\n");
    for (int j = 0; j < number_of_blocks; ++j) {
        for (int i = 0; i < 16; i++) {
            printf("%02x ", encrypted_blocks[j * number_of_blocks + i]);
        }
        printf("\n");
    }

	printf("\n");

    decrypt_blocks(decrypted_blocks, encrypted_blocks, number_of_blocks, expanded_key);

	printf("Original input_message (after inv cipher):\n");
    for (int j = 0; j < number_of_blocks; ++j) {
        for (int i = 0; i < 16; i++) {
            printf("%02x ", decrypted_blocks[j * number_of_blocks + i]);
        }
        printf("\n");
    }

	printf("\n");

	free(expanded_key);
    free(encrypted_blocks);
    free(decrypted_blocks);

	return 0;
}

