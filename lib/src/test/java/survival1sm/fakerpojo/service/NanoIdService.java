package survival1sm.fakerpojo.service;

import static java.util.Map.entry;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import survival1sm.fakerpojo.enums.NanoPrefix;

public class NanoIdService {

  private static final char[] options = new char[]{'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
      'S', 'T', 'V', 'W',
      'X', 'Z', 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

  private static final Map<NanoPrefix, String> prefixMap = Map.ofEntries(
      entry(NanoPrefix.project, "PRJ"),
      entry(NanoPrefix.status, "STS"),
      entry(NanoPrefix.product, "PRD"),
      entry(NanoPrefix.type, "TYP"));

  public static String getNanoId(NanoPrefix prefix) {
    Random random = new Random();
    return prefixMap.get(prefix) + "-" + NanoIdUtils.randomNanoId(random, options, 10);
  }

  public static NanoPrefix getPrefixFromValue(String nanoPrefixValue) {
    return NanoIdService.prefixMap.entrySet().stream().filter(entry -> nanoPrefixValue.equals(entry.getValue()))
        .findFirst()
        .map(Entry::getKey)
        .orElse(null);
  }
}
