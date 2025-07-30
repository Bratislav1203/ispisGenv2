package model;

import java.util.HashMap;
import java.util.Map;

public class VoziloRegistry {

    private static final Map<Integer, VoziloInfo> vozila = new HashMap<>();

    static {
        vozila.put(644, new VoziloInfo(
            new String[]{
                "Euroscan@",
                "EN12830 T B C1. ATP-MUC 1048/1049 TS",
                " ",
                "L A Z I C",
                "AA-644RA. S/N: 11316001"
            },
            true, true, "AA-644RA", "11316001"
        ));

        vozila.put(406, new VoziloInfo(
            new String[]{
                "Lazic_ _",
                "AA 406RA, SNR  11211399"
            },
            false, true, "AA 406RA", "11211399"
        ));

        vozila.put(820, new VoziloInfo(
            new String[]{
                "Euroscan@",
                "EN12830 T B C1. ATP-MUC 1048/1049 TS",
                " ",
                "L A Z I C",
                "AA-820RA. S/N: 11316001"
            },
            true, true, "AA-820RA", "11316001"
        ));

        vozila.put(870, new VoziloInfo(
            new String[]{
                "Euroscan@",
                "EN12830 T B C1. ATP-MUC 1048/1049 TS",
                " ",
                "L A Z I C",
                "AA-870RA. S/N: 11316235"
            },
            true, true, "AA-870RA", "11316235"
        ));

        vozila.put(447, new VoziloInfo(
            new String[]{
                "Carrier - DataCOLD 500",
                "EN12830 T B C1. ATP-MUC 1036/1037 TS",
                " ",
                "L A Z I C",
                "AA-447RA. S/N: 10729009"
            },
            true, true, "AA-447RA", "10729009"
        ));

        // ✅ Novo vozilo 1 – AB 259RA (Lazic_ Trans_)
        vozila.put(259, new VoziloInfo(
            new String[]{
                "Lazic_ Trans_",
                "AB 259RA, SNR  11211444"
            },
            false, true, "AB 259RA", "11211444"
        ));

        // ✅ Novo vozilo 2 – AB260RA (Euroscan, Lazic transport doo)
        vozila.put(260, new VoziloInfo(
            new String[]{
                "Euroscan@",
                "EN12830 T B C1. ATP-MUC 1048/1049 TS",
                "Lazic transport doo",
                "AB260RA. S/N: 11316667"
            },
            true, true, "AB260RA", "11316667"
        ));
        vozila.put(131, new VoziloInfo(
        	    new String[]{
        	        "Euroscan@",
        	        "EN12830 T B C1. ATP-MUC 1048/1041",
        	        "LAZIC",
        	        "AB-131RA. S/N: 11316036"
        	    },
        	    true, true, "AB131RA", "11316036"
        	));
    }

    public static VoziloInfo get(int regBr) {
        return vozila.get(regBr);
    }

    public static Map<Integer, VoziloInfo> getAll() {
        return vozila;
    }
}
