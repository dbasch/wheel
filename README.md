# Wheel

A library to store and manage login credentials.

### Why?

You are writing an app. You want users to sign up with usernames and passwords. It's a simple concept, but there are so many little details to remember. Are you enforcing a minimum password length? Are you sure your authentication data is stored securely?

We have all seen news articles about password leaks at different organizations. A large number of people reuse the same password across sites. If attackers can identify pairs of passwords and usernames, they may use this information everywhere.

**Wheel** enforces a minimum password length of 12 characters. It never stores or records a plaintext password. It stores one-way hashes of (password + random salt) with a level of entropy that makes a bruce-force attack infeasible. You shouldn't have to reinvent these things :)

## Usage

See docs

## License

Copyright © 2014 Diego Basch

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
