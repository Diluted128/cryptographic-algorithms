import sympy
import math
from bitwise_operations import compute_xor, rotate_right, shift_right

block_size = 512

sub_block_size = 32

size_postfix_length = 64

input_blocks = []


def get_H_values():
    primes = list(sympy.primerange(0, 20))

    print("\nH hash values: ")

    i = 0
    H = []
    for prime in primes:
        prime_root_square = str(math.sqrt(prime))
        index_of_dot = prime_root_square.index('.')
        binary_after_decimal = bin(int(prime_root_square[index_of_dot + 1:]))
        H.append(binary_after_decimal[2:34])
        print(f'H{i} = ' + binary_after_decimal[2:34])
        i += 1

    return H


def get_K_values():
    primes = list(sympy.primerange(0, 313))

    print("\nK hash values: ")

    i = 0
    K = []
    for x in range(len(primes)):
        cube_root_square = str(primes[x] ** (1 / 3))
        index_of_dot = cube_root_square.index('.')
        binary_after_decimal = bin(int(cube_root_square[index_of_dot + 1:]))
        K.append(binary_after_decimal[2:34])
        print(f'K{i} = ' + binary_after_decimal[2:34])
        i += 1

    return K


def get_prepared_blocks(input_bits):
    i = sub_block_size
    e = 0

    while i <= block_size:
        input_blocks.append(input_bits[i - sub_block_size:i])
        print(f'M{e} = ' + input_bits[i - sub_block_size:i])
        i += sub_block_size
        e += 1

    print("auto generated")
    for e in range(16, 64):
        input_blocks.append(_generate_hash_block(e))
        print(f'M{e} = ' + input_blocks[e])

    return input_blocks


def get_prepared_message(input_string):
    print('\n*---DATA PREPARATION---*\n')

    input_bits = ''.join(format(ord(char), '08b') for char in input_string)

    input_size_bits = ''.join(format(ord(char), '08b') for char in str(len(input_string)))

    for x in range(size_postfix_length - len(input_size_bits)):
        input_size_bits = "0" + input_size_bits

    s = int(len(input_bits) / block_size)

    how_many_to_fill = ((s + 1) * block_size - len(input_bits)) - size_postfix_length

    print('Block parts: ')
    print("Input string -> [" + input_bits + ']')

    input_bits = input_bits + "1"
    fill = '1'

    for x in range(how_many_to_fill - 1):
        input_bits = input_bits + "0"
        fill += '0'

    print('Fill -> [ ' + fill + ']')

    input_bits += input_size_bits

    print('Input size -> [' + str(input_size_bits) + ']\n')

    return input_bits


def _generate_hash_block(t):
    return compute_xor(
        _compute_sigma_one(input_blocks[t - 2]),
        compute_xor(
            input_blocks[t - 7],
            compute_xor(input_blocks[t - 16], _compute_sigma_zero(input_blocks[t - 15]))
        )
    )


def _compute_sigma_zero(value):
    right_seven_rotation = rotate_right(7, value)
    right_eighteen_rotation = rotate_right(18, value)
    right_shift_three = shift_right(3, value)

    return compute_xor(compute_xor(right_seven_rotation, right_eighteen_rotation), right_shift_three)


def _compute_sigma_one(value):
    right_seven_rotation = rotate_right(17, value)
    right_eighteen_rotation = rotate_right(19, value)
    right_shift_three = shift_right(10, value)

    return compute_xor(compute_xor(right_seven_rotation, right_eighteen_rotation), right_shift_three)




