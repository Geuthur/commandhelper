/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laytonsmith.aliasengine.functions;

import com.laytonsmith.aliasengine.CancelCommandException;
import com.laytonsmith.aliasengine.ConfigRuntimeException;
import com.laytonsmith.aliasengine.Constructs.CArray;
import com.laytonsmith.aliasengine.Constructs.CString;
import com.laytonsmith.aliasengine.Constructs.CVoid;
import com.laytonsmith.aliasengine.Constructs.Construct;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author Layton
 */
public class Environment {
    public static String docs(){
        return "Allows you to manipulate the environment around the player";
    }
    
    @api public static class get_block_at implements Function{

        public String getName() {
            return "get_block_at";
        }

        public Integer[] numArgs() {
            return new Integer[]{1, 3};
        }

        public String docs() {
            return "string {x, y, z | xyzArray} Gets the id of the block at x, y, z. This function expects "
                    + "either 1 or 3 arguments. If 1 argument is passed, it should be an array with the x, y, z"
                    + " coordinates. The format of the return will be x:y where x is the id of the block, and"
                    + " y is the meta data for the block. All blocks will return in this format, but blocks"
                    + " that don't have meta data normally will return 0 in y.";
        }

        public boolean isRestricted() {
            return true;
        }

        public void varList(IVariableList varList) {}

        public boolean preResolveVariables() {
            return true;
        }

        public String since() {
            return "3.0.2";
        }

        public Construct exec(int line_num, Player p, Construct... args) throws CancelCommandException, ConfigRuntimeException {
            int x;
            int y;
            int z;
            if(args.length == 1){
                if(args[0] instanceof CArray){
                    CArray ca = (CArray)args[0];
                    if(ca.size() == 3){
                        x = Static.getInt(ca.get(0));
                        y = Static.getInt(ca.get(1));
                        z = Static.getInt(ca.get(2));
                    } else {
                        throw new CancelCommandException("get_block_at expects param 1 to be an array with 3 arguments");
                    }
                } else {
                    throw new CancelCommandException("get_block_at expects param 1 to be an array");
                }
            } else {
                x = Static.getInt(args[0]);
                y = Static.getInt(args[1]);
                z = Static.getInt(args[2]);
            }
            Block b = p.getWorld().getBlockAt(x, y, z);
            return new CString(b.getTypeId() + ":" + b.getData(), line_num);
        }
        
    }
    
    @api public static class set_block_at implements Function{

        public String getName() {
            return "set_block_at";
        }

        public Integer[] numArgs() {
            return new Integer[]{2, 4};
        }

        public String docs() {
            return "void {x, y, z, id | xyzArray, id} Sets the id of the block at the x y z coordinates specified. If the"
                    + " first argument passed is an array, it should be x y z coordinates. id must"
                            + " be a blocktype identifier similar to the type returned from get_block_at, except if the meta"
                            + " value is not specified, 0 is used.";
        }

        public boolean isRestricted() {
            return true;
        }

        public void varList(IVariableList varList) {}

        public boolean preResolveVariables() {
            return true;
        }

        public String since() {
            return "3.0.2";
        }

        public Construct exec(int line_num, Player p, Construct... args) throws CancelCommandException, ConfigRuntimeException {
            int x;
            int y;
            int z;
            String id;
            if(args.length == 2 && args[0] instanceof CArray){
                CArray ca = (CArray)args[0];
                if(ca.size() != 3){
                    throw new CancelCommandException("set_block_at expects the parameter 1 to be an array with 3 elements.");
                }
                x = Static.getInt(ca.get(0));
                y = Static.getInt(ca.get(1));
                z = Static.getInt(ca.get(2));
                id = args[1].val();
                
            } else {
                x = Static.getInt(args[0]);
                y = Static.getInt(args[1]);
                z = Static.getInt(args[2]);
                id = args[3].val();
            }
            
            Block b = p.getWorld().getBlockAt(x, y, z);
            StringBuilder data = new StringBuilder();
            StringBuilder meta = new StringBuilder();
            boolean inMeta = false;
            for(int i = 0; i < id.length(); i++){
                Character c = id.charAt(i);
                if(!inMeta){
                    if(!Character.isDigit(c) && c != ':'){
                        throw new CancelCommandException("id must be formatted as such: 'x:y' where x and y are integers");
                    }
                    if(c == ':'){
                        inMeta = true;
                        continue;
                    }
                    data.append(c);
                } else {
                    meta.append(c);
                }
            }
            if(meta.length() == 0){
                meta.append("0");
            }
            
            int idata = Integer.parseInt(data.toString());
            byte imeta = Byte.parseByte(meta.toString());
            b.setTypeId(idata);
            b.setData(imeta);
            
            return new CVoid(line_num);
        }
        
    }
    
}
