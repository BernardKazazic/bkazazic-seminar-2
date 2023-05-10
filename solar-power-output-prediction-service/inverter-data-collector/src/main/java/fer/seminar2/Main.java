package fer.seminar2;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.Register;
import util.HoldingRegisterDataParser;

import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        String address = "192.168.66.130";
        int port = 502;

        TCPMasterConnection connection = new TCPMasterConnection(InetAddress.getByName(address));
        connection.setPort(port);
        connection.connect();
        connection.setTimeout(1000);

        ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
        transaction.setRetries(0);
        transaction.setReconnecting(false);

        int startRegisterAddress = 40092;
        int registerCount = 2;

        ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(startRegisterAddress, registerCount);
        request.setUnitID(1);
        transaction.setRequest(request);
        transaction.execute();

        ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) transaction.getResponse();

        Register[] registers = response.getRegisters();

        float ACPower = HoldingRegisterDataParser.parseSwFloat(registers[0], registers[1]);

        System.out.println("Current AC power: " + ACPower + "W");
        connection.close();
    }
}