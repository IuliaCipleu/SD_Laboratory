import { Role } from "./role";

export interface User{
    id: string,
    username: string,
    name: string,
    surname: string,
    password: string,
    dateOfBirth: Date,
    email: string,
    roles: Role[];
}