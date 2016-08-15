package br.com.renatorfr.test.wifidirect.wifidirecttest;

public enum LogCommands {
    WIFIP2P_ENABLED("Wifi P2P enabled \\o/"), WIFIP2P_DISABLED("Wifi P2P disabled :("),
    DISCOVER_PEERS_SUCCESS("Peers discover success"), DISCOVER_PEERS_FAILURE("Peers discover failure"),
    PEERS_FOUND("Peers found \\o/"), PEER("\tPeer: "), PEER_CONNECTED("Peer connected \\o/"),
    PEER_NOT_CONNECTED("Peer not connected :(");

    private String logCommand;

    LogCommands(String logCommand) {
        this.logCommand = logCommand;
    }

    public String getCommand() {
        return logCommand;
    }
}
