# Block Counter (Hytale)

**Block Counter** is a utility mod for Hytale designed to help builders and technical players. It allows you to select a region and get a precise breakdown of every block type within those boundaries.

## License

This project is licensed under the **CC0 1.0 Universal License**. See the [LICENSE](LICENSE) file for more details.

## Commands

/blockcounter-setposition : allows the player to set a first and second position to count blocks in (the algorithm executes after the second position is set).

/blockcounter-countblocks <first_position> <second_position> : allows the player to count blocks between <first_position> and <second_position>

/blockcounter-setlimit <number>: sets a limit of the amount of blocks/loops will be executed before returning a result (to avoid the server crashing after too many loops)

/blockcounter-help : helper utility to know the commands of the mod

---

*Maintained by [Demomaker](https://github.com/Demomaker)*

## How to develop

Step 1 : [clone the repo](https://github.com/Demomaker/Block-Counter-Hytale)

Step 2 : open the repo in IntelliJ or some other IDE that supports java development

Step 3 : go in your Hytale game folder and find HytaleServer.jar

Step 4 : put the HytaleServer.jar at the root of the project (where you cloned the repo)

Step 5 : `maven install` & `maven build`

Step 6 : code with that

Step 7 : `maven package`

Step 8 : find the `target` folder in the repo after generating the package and take the generated file from there

Step 9 : put the generated file in a world's mod folder in Hytale

Step 10 : launch Hytale and test the mod

Step 11 : if changes needed, code again and repeat steps 7 to 10. otherwise step 12

Step 12 : [change version of the mod](pom.xml), commit and push the changes onto github : 

Step 12a0 : `git pull`

Step 12a : `git add .`

Step 12b : `git commit -m "summary of changes"`

Step 12c : `git push`

Step 13 : [publish the package in a github release with version tag changed](https://github.com/Demomaker/Block-Counter-Hytale/releases/new)

Step 14 : upload to [modtale](https://modtale.net/mod/demomaker-s-block-counter-35cd5c91-b3f8-4d15-ab13-413ae4b58206) and [curseforge](https://legacy.curseforge.com/hytale/mods/demomakers-block-counter)

Step 15 : wait and see if things break lol
