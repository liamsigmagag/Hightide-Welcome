# HighTide Welcome Auto Reply (Fabric 1.21.11)

A client-side Fabric mod that sends `Welcome!` when the client receives the server announcement:

`New → Welcome <player name> to Hightide Survival! (#<number>)`

The player name and number can vary. It listens to Fabric's **GAME/system-message** event, not the player **CHAT** event, so a normal player typing the same text should not trigger it.

## Requirements

- Minecraft Java Edition 1.21.11
- Fabric Loader 0.18.5 or newer
- Fabric API for Minecraft 1.21.11
- Java 21 JDK to build

## Build

1. Install Java 21 JDK and Gradle (or open this project in IntelliJ IDEA and let it import the Gradle project).
2. From this folder, run `gradle build`.
3. The mod JAR will be created in `build/libs/`.
4. Put `hightide-welcome-1.0.0.jar` in your Minecraft instance's `mods` folder, along with Fabric API.

## Notes

- The reply is queued for 2 client ticks (about 100 ms at 20 TPS). Minecraft client ticks can pause or run late, so this is approximately—not a real-time guaranteed—100 ms.
- This sends a normal player chat message. The server may block automated chat or consider repeated messages spam.
- The regex intentionally requires the `New → Welcome ... to Hightide Survival! (#...)` structure and will not react to arbitrary system messages.


## Build automatically on GitHub (no local Gradle needed)

1. Create a GitHub account and create a new **public** repository named `hightide-welcome`.
2. Extract this ZIP on your PC. Upload the files and folders inside the extracted `hightide_welcome_mod` folder to the repository root. Make sure the `.github/workflows/build.yml` file is included.
3. Open the repository's **Actions** tab. Select **Build HighTide Welcome Mod**, then click **Run workflow** (or let the first push run automatically).
4. Open the completed workflow run and download the artifact named `hightide-welcome-fabric-1.21.11`. That ZIP contains the compiled JAR.
5. Put the `.jar` inside your Minecraft instance's `mods` folder. Keep Fabric API installed.

GitHub's free Actions quota should be enough for a small project, subject to GitHub's current limits. The workflow compiles the project; the result still needs an in-game test on your server.
