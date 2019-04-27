# GitUserApp
+ Android(Kotlin)/ Apollo Cli/ GraphQL

This is an android app for testing Github's GraphQL.

## Prerequisites
- git
- nodejs
- Github token

## Getting Started

### Generate Github Token
1. Visit [https://github.com/settings/tokens](https://github.com/settings/tokens).

2. Click **Generate new token**.

        Token Description: (your computer name)
        Scopes:
            [X] repo
                [X] repo:status
                [X] repo_deployment
                [X] public_repo
                [X] repo:invite

3. Click **Generate token**.

4. Copy the generated string to a safe place, such as a password safe.

### Config
It's important to replace the github token in `keystore.properties` file in project root with your own token.


## Functionalities

* Search for GitHub User
* View public repositories

## Support
*From Android 6.0 to Android 9.0*

## Releases


## Images:

![MainActivity](./IMAGEAPP/emptyview.PNG)
![GetRepositoriesActivity](./IMAGEAPP/searchusers.PNG)
![GetRepositoriesActivity](./IMAGEAPP/repositories.PNG)

