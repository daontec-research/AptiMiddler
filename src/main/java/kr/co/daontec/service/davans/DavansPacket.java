package kr.co.daontec.service.davans;

import com.google.common.primitives.Bytes;

import java.util.ArrayList;
import java.util.List;

public class DavansPacket {
    int bcommandidx;
    String gsdevice_id; //"01010501";
    List<Byte> lstpacket = new ArrayList<>();
    private final static int DEVICE_ID_LEN = 8;
    private final static int CMD_LEN = 4;
    private final static int HEADER_SIZE = DEVICE_ID_LEN + CMD_LEN;

    public DavansPacket(){}
    public DavansPacket(int bcommandidx, String gsdevice_id) {
        this.bcommandidx = bcommandidx;
        this.gsdevice_id = gsdevice_id;
    }


    public byte[] get_packet(){
        return Bytes.toArray(lstpacket);
    }

    public void fnsetbodyinfo(byte[] bbytedata){

        byte[] cmdArray = intToByteArray(bcommandidx);
        byte[] devArray = fnstobbylen(gsdevice_id,8);
        byte[] lenArray = intToByteArray(bbytedata.length + cmdArray.length + devArray.length);

        lstpacket.addAll(Bytes.asList(lenArray));
        lstpacket.addAll(Bytes.asList(devArray));
        lstpacket.addAll(Bytes.asList(cmdArray));
        lstpacket.addAll(Bytes.asList(bbytedata));
    }

    public byte[] fnstobbylen( String sData, int nlen )
    {
        try {
          String  devId = sData.substring (0, sData.length() > nlen ? nlen : sData.length());
          return  devId.getBytes("UTF-8");
        } catch ( Exception ex) {
            //
        }
        return null;
    }

    public byte[] intToByteArray(int value) {
        byte[] byteArray = new byte[4];
        byteArray[3] = (byte)(value >> 24);
        byteArray[2] = (byte)(value >> 16);
        byteArray[1] = (byte)(value >> 8);
        byteArray[0] = (byte)(value);
        return byteArray;
    }

    public static int getDEVICE_ID_LEN(){
        return DEVICE_ID_LEN;
    }
    public static int getCMD_LEN(){
        return CMD_LEN;
    }
    public static int getHEADER_SIZE(){
        return HEADER_SIZE;
    }
}

