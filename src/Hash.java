import java.util.Arrays;

/**
 * Wrapper class that represents a byte array, along with
 * operations we would like to perform on Hash objects. 
 * 
 * @author Tim Yu
 * @author Nye Tenerelli
 * @author Keely Miyamoto
 */
public class Hash {
  // +--------+
  // | Fields |
  // +--------+-------------------------------------------------------------------------------------
  /**
   * The hashed data.
   */
  byte[] data;

  // +--------------+
  // | Constructor |
  // +-------------+--------------------------------------------------------------------------------
  /**
   * Hash(byte[] data): constructs a new Hash object 
   * that contains the given hash (as _data, an array of bytes).
   * @param _data
   */
  public Hash(byte[] data) {
    this.data = data;
  } // Hash(byte[] _data)

  // +----------+
  // | Methods |
  // +---------+----------------------------------------------------------------------------------
  /**
   * Method to get the hash contained in this object.
   * @return this.data
   */
  public byte[] getData() {
    return this.data;
  } // getData()

  /**
   * Returns true iff this hash meets the criteria for validity, 
   * i.e., its first three indices contain zeroes.
   * 
   * @return boolean
   */
  public boolean isValid() {
    // Check that hash length is not less than 3.
    if (this.data.length < 3) {
      return false;
    } // if

    // Check each of the first 3 indices in hash are 0.
    for (int i = 0; i < 3; i++) {
      if (Byte.toUnsignedInt(this.data[i]) != 0) {
        return false;
      } // if
    } // for

    // If so, return true.
    return true;
  } // getData()

  /**
   * Returns the string representation of the hash as a string 
   * of hexadecimal digits, 2 digits per byte.
   * @return String
   */
  public String toString() {
    // Create string to be returned.
    String ret = "";
    // For each byte element in this.data...
    for (byte cur : this.data) {
      // Convert current byte 'cur' to an Unsigned Int of at least 2 digits;
      // append to 'ret'.
      ret += String.format("%02x", Byte.toUnsignedInt(cur));
    } // for
    return ret;
  } // toString()

  /**
   * Returns true if this hash is structurally equal to the argument.
   * @param Object
   * @return boolean 
   */
  public boolean equals(Object other) {
    // Check if other is an instance of Hash.
    if (other instanceof Hash) {
      // If so, cast other to type Hash.
      Hash o = (Hash) other;
      // Perform the appropriate equality check on the two Hash object’s arrays.
      return Arrays.equals(this.getData(), o.getData());
    } // if

    // Otherwise, return false.
    return false;
  } // equals(Object)
  
} // class Hash
