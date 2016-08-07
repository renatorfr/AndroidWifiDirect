package br.com.renatorfr.test.wifidirect.wifidirecttest;

public interface CommandHandler {
    String TAG = "COMMAND_HANDLER";
    void handle(Commands command);
    void log(LogCommands logCommand);
    void log(LogCommands logCommand, String arg);
}
