def compute_xor(a, b):
    result = ''
    for i in range(len(a)):
        if a[i] != b[i]:
            result += '1'
        else:
            result += '0'
    return result


def rotate_right(rotation, value):
    return value[len(value) - rotation:] + value[:len(value) - rotation]


def shift_right(rotation, value):
    a = value[:len(value) - rotation]

    for i in range(rotation):
        a = "0" + a

    return a
