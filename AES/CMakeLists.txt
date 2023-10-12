cmake_minimum_required(VERSION 3.26)
project(AES C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(AES
        aes.c
        aes.h
        gmult.c
        gmult.h
        main.c)
