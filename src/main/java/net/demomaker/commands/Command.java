package net.demomaker.commands;

import java.util.ArrayList;
import java.util.List;

public class Command<T extends BlockCounterCommand> {
    private final String name;
    private final String description;
    private final String helperInfo;
    private BlockCounterCommand commandDefinition;

    public static List<Command> getCommands() {
        var commandList = new ArrayList<Command>();
        commandList.add(
                new Command<CountBlocksCommand>(
                        CountBlocksCommand.class,
                        "blockcounter-countblocks",
                        "count blocks",
                        "usage : /blockcounter-countblocks first_position_x first_position_y first_position_z second_position_x second_position_y second_position_z"));
        commandList.add(new Command<HelpCommand>(
                HelpCommand.class,
                "blockcounter-help",
                "help for the block counter mod",
                "Block Counter allows you to count blocks in your world! Use the appropriate commands (/blockcounter-countblocks, /blockcounter-setposition)"));
        commandList.add(new Command<SetPositionCommand>(
                SetPositionCommand.class,
                "blockcounter-setposition",
                "set position so that it is simpler to execute the counting algorithm",
                "usage : /blockcounter-setposition"));
        commandList.add(new Command<SetBlockLimitCommand>(
                SetBlockLimitCommand.class,
                "blockcounter-setlimit",
                "set the limit of amount of blocks the algorithm will count",
                "usage /blockcounter-setlimit <number>"
        ));
        return commandList;
    }

    public Command(Class<T> clazz, String name, String description, String helperInfo) {
        this.name = name;
        this.description = description;
        this.helperInfo = helperInfo;
        try {
            this.commandDefinition = clazz.getDeclaredConstructor(String.class, String.class)
                    .newInstance(name, description)
                    .setHelperInfo(helperInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getHelperInfo() {
        return this.helperInfo;
    }

    public BlockCounterCommand getCommandDefinition() {
        return this.commandDefinition;
    }
}
