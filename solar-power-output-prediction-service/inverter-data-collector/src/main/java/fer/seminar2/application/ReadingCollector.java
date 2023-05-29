package fer.seminar2.application;

import fer.seminar2.infrastructure.configuration.InverterConfiguration;
import lombok.NonNull;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.Register;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ReadingCollector {
    @NonNull
    private final InverterConfiguration configuration;
    @NonNull
    private final List<TCPMasterConnection> connections;

    public ReadingCollector(InverterConfiguration configuration) {
        this.configuration = configuration;
        this.connections = new ArrayList<>();
        addShutdownHook();

        for (String address : configuration.getAddress()) {
            try {
                TCPMasterConnection connection = new TCPMasterConnection(InetAddress.getByName(address));
                connection.setPort(configuration.getPort());
                connection.connect();
                connection.setTimeout(1000);
                connections.add(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public float getCurrentACPower() throws ModbusException {
        List<Float> ACPowers = new ArrayList<>();

        for (TCPMasterConnection connection : connections) {
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
            transaction.setRetries(0);
            transaction.setReconnecting(false);

            ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(configuration.getStartRegister(), configuration.getRegisterCount());
            request.setUnitID(1);
            transaction.setRequest(request);
            transaction.execute();

            ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) transaction.getResponse();

            Register[] registers = response.getRegisters();

            float ACPower = parseFloat(registers[0], registers[1]);

            ACPowers.add(ACPower);
        }

        return ACPowers.stream().reduce(0.0f, Float::sum);
    }

    private float parseSwFloat(Register register0, Register register1) {
        int mostSignificantByte = register0.toUnsignedShort();
        int leastSignificantByte = register1.toUnsignedShort();

        int combinedValue = (mostSignificantByte << 16) | leastSignificantByte;

        return ByteBuffer.allocate(4).putInt(combinedValue).flip().getFloat();
    }

    private float parseFloat(Register register0, Register register1) {
        int mostSignificantByte = register0.toUnsignedShort();
        int leastSignificantByte = register1.toUnsignedShort();

        int combinedValue = (leastSignificantByte << 16) | mostSignificantByte;

        return Float.intBitsToFloat(combinedValue);
    }

    private void closeConnections() {
        for (TCPMasterConnection connection : connections) {
            connection.close();
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeConnections));
    }
}
