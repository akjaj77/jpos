package com.taskincomplete.jpos;
import java.io.IOException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.channel.NACChannel;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.channel.BCDChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.PostPackager;

public class SampleRequest {

	public static void main(String[] args) throws IOException, ISOException {
		// Create Packager based on XML that contain DE type
		GenericPackager packager = new GenericPackager("CustomConfig.xml");

		// Create ISO Message
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		 //isoMsg.setHeader("6003560000".getBytes());
		isoMsg.setMTI("0200");
		 isoMsg.set(2, "327A1DE46B9F0C123");
		isoMsg.set(3, "020000");
		 isoMsg.set(4, "000000056555");
		// isoMsg.set(7, "0915070018");
		isoMsg.set(11, "000005");
		 isoMsg.set(12, "112609");
		 isoMsg.set(13, "0202");
		 isoMsg.set(14, "1611");
		// isoMsg.set(15, "0917");
		// isoMsg.set(18, "6011");
		// isoMsg.set(19, "356");

		isoMsg.set(22, "0051");
		isoMsg.set(24, "0356");
		 isoMsg.set(25, "00");

		// isoMsg.set(35, "7241760000024490=129950009999218");
		 isoMsg.set(37, "503305560001");
		 isoMsg.set(38, "509487");
		// isoMsg.set(39, "00");
		isoMsg.set(41, "24312329");
		isoMsg.set(42, "037022000042125");
		// isoMsg.set(43, "SBI KODA JAHANABAD     FATEHPUR     UPIN");
		// isoMsg.set(49, "356");
		// isoMsg.set(52, "1234");
		isoMsg.set(54, "000000001000");
		isoMsg.set(55, "013182027C00950500800480009A031502029C01005F2A0203565F3401009F02060000000555559F03060000000000009F0902008C9F100706010A03A020029F1A0203569F1E08534349464D5836359F260842D82C543F5CE2D39F2701809F3303E0F0C89F34030203009F3501229F3602002B9F3704DD87A06D9F4104000000059F530152");
		isoMsg.set(60, "001200005555");
		isoMsg.set(62, "000602");
		// isoMsg.set(63,
		// "001002010020020400300593500004015PETROL018003258034009286497331");
		// isoMsg.set(100, "504493");
		// isoMsg.set(102, "00000000000000000782310110011094");
		// BCDChannels c = new BCDChannels("223.31.9.194",
		// 9004,packager,ISOUtil.str2bcd("6003560000", false) );
		ASCIIChannel c = new ASCIIChannel("localhost", 5000, packager/*
																		 * , ISOUtil.str2bcd("600356", false)
																		 */);
		logISOMsg(isoMsg);
		System.out.println(ISOUtil.hexdump(isoMsg.pack()));
		//byte[] data = isoMsg.pack();
		//System.out.println("RESULT : " + new String(data));
		c.connect();
		c.send(isoMsg);
		// print the DE list
		// System.out.println("Response:"+c.receive());
		// org.jpos.iso.IFA_LLNUM
		//System.out.println(ISOUtil.hexString(data));
//		c.receive();
		ISOMsg response = c.receive();
		System.out.println("****************Response *********************");
		logISOMsg(response);
		// Get and print the output result

	}

	private static void logISOMsg(ISOMsg msg) {
		System.out.println("----ISO MESSAGE-----");
		try {
			System.out.println("  MTI : " + msg.getMTI());
			for (int i = 1; i <= msg.getMaxField(); i++) {
				if (msg.hasField(i)) {
					System.out.println("    Field-" + i + " : "
							+ msg.getString(i));
				}
			}
		} catch (ISOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("--------------------");
		}

	}

}