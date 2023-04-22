import data_preparation as data
from bitwise_operations import compute_xor, rotate_right, shift_right

H = []
K = []

input_blocks = []


def compress_hashes():

    H_copy = H[:]

    for i in range(64):
        T1 = compute_xor(
                H[7],
                compute_xor(
                    compute_sigma_one(H[4]),
                    compute_xor(
                        compute_choose_operation(H[4], H[5], H[6]),
                        compute_xor(K[i], input_blocks[i])
                    )
                )
            )

        T2 = compute_xor(compute_sigma_zero(H[0]), compute_majority_operation(H[0], H[1], H[2]))
        H[7] = H[6]
        H[6] = H[5]
        H[5] = H[4]
        H[4] = compute_xor(H[3], T1)
        H[3] = H[2]
        H[2] = H[1]
        H[1] = H[0]
        H[0] = compute_xor(T1, T2)

    print('\nCompressed hash values:')
    for i in range(len(H)):
        print(f'H{i} ' + H[i])

    hash = ''
    print('\nXored with initial hash values:')
    for i in range(len(H)):
        H[i] = compute_xor(H_copy[i], H[i])
        hash += H[i]
        print(f'H{i} ' + H[i])

    print(f"\nFinal compressed hash: \n{_bin_to_hex(hash)}")


def compute_majority_operation(a, b, c):
    result = ''
    for i in range(len(a)):
        el_sum = int(a[i]) + int(b[i]) + int(c[i])

        if el_sum > 1:
            result += '1'
        else:
            result += '0'

    return result

def compute_choose_operation(e, f, g):
    result = ''
    for i in range(len(e)):
        if e[i] == '0':
            result += g[i]
        else:
            result += f[i]

    return result


def compute_sigma_zero(input):
    right_two_rotation = rotate_right(2, input)
    right_thirty_rotation = rotate_right(13, input)
    right_twentytwo_rotation = rotate_right(22, input)

    return compute_xor(
        right_twentytwo_rotation,
        compute_xor(right_two_rotation, right_thirty_rotation)
    )


def compute_sigma_one(input):
    right_six_rotation = rotate_right(6, input)
    right_eleven_rotation = rotate_right(11, input)
    right_twentyfive_rotation = rotate_right(25, input)

    return compute_xor(
        right_six_rotation,
        compute_xor(right_eleven_rotation, right_twentyfive_rotation)
    )


def _bin_to_hex(binary_string):
    binary_groups = [binary_string[i:i + 4] for i in range(0, len(binary_string), 4)]
    hex_values = [hex(int(binary_group, 2))[2:] for binary_group in binary_groups]
    hex_string = ''.join(hex_values)
    return hex_string


if __name__ == '__main__':
    input_string = "Ala ma kota"

    H = data.get_H_values()
    K = data.get_K_values()

    input_bits = data.get_prepared_message('Ala ma kota')
    input_blocks = data.get_prepared_blocks(input_bits)
    compress_hashes()