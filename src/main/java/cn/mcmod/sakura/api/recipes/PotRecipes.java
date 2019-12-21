package cn.mcmod.sakura.api.recipes;

import java.util.ArrayList;

import cn.mcmod.sakura.util.RecipesUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class PotRecipes {
    public static ArrayList<PotRecipes> potRecipesList = new ArrayList<PotRecipes>();

    public ItemStack resultItem = ItemStack.EMPTY;
    public Object mainItem = ItemStack.EMPTY;
    public FluidStack fluid = null;
    public ArrayList<Object> subItems = new ArrayList<Object>();
    public boolean enchantment = false;

    public PotRecipes() {
    }

    public PotRecipes(ItemStack result, Object main, FluidStack fluidStack) {
        this.setPotRecipes(result, main, new Object[]{}, fluidStack);
    }

    public PotRecipes(ItemStack result, Object main, Object[] subList, FluidStack fluidStack) {
        this.setPotRecipes(result, main, subList, fluidStack);

    }

//    public PotRecipes(ItemStack result, String main, FluidStack fluidStack) {
//        this.setPotRecipes(result, main, new Object[]{}, fluidStack);
//
//    }
//
//    public PotRecipes(ItemStack result, String main, Object[] subList, FluidStack fluidStack) {
//        this.setPotRecipes(result, main, subList, fluidStack);
//
//    }

    public void setPotRecipes(ItemStack result, Object main, Object[] subList, FluidStack fluidStack) {
        this.clear();
        if(main instanceof ItemStack || main instanceof String)
        mainItem = main;
        else throw new IllegalArgumentException("Main Item is not a ItemStack or Ore Dictionary");
    	for (Object o : subList) {
			if(o instanceof ItemStack||o instanceof String)
				subItems.add(o);
			else throw new IllegalArgumentException("Sub Item is not a ItemStack or Ore Dictionary");
        }
        resultItem = result.copy();
        fluid = fluidStack.copy();
    }

//    public void setPotRecipes(ItemStack result, String mainOreName, Object[] subList, FluidStack fluidStack) {
//        if(!OreDictionary.getOres(mainOreName).isEmpty()){
//    	for (int i2 = 0; i2 < OreDictionary.getOres(mainOreName).size(); i2++) {
//            this.clear();
//            mainItem = OreDictionary.getOres(mainOreName).get(i2).copy();
//            	for (Object o : subList) {
//        			if(o instanceof ItemStack||o instanceof String)
//        				subItems.add(o);
//        			else throw new IllegalArgumentException("Not a itemStack or Ore Dictionary");
//                }
//
//            resultItem = result.copy();
//            fluid = fluidStack.copy();
//        }
//        }else throw new IllegalArgumentException("Invalid Main Ore Dictionary");
//    }

    /**
     * 初期化
     */
    public void clear() {
        resultItem = ItemStack.EMPTY;
        mainItem = ItemStack.EMPTY;
        fluid = null;
        subItems = new ArrayList<Object>();
    }

    public ItemStack getResultItemStack() {
        return resultItem.copy();
    }

    public FluidStack getResultFluid() {
        return fluid.copy();
    }

    public FluidStack getResultFluid(FluidStack fluidStack) {
    	if(fluid.amount<=0) 
    		return fluidStack;
    	else
        if (fluidStack.isFluidEqual(fluid)) {
            return fluidStack;
        } else {
            return null;
        }
    }

    public ItemStack getResult(IInventory inventory) {

        ItemStack retStack = ItemStack.EMPTY;
//        if (!ItemStack.areItemsEqual(mainItem, inventory.getStackInSlot(0))) {
//            return retStack;
//        }
        if(mainItem instanceof ItemStack){
          if (!ItemStack.areItemsEqual((ItemStack) mainItem, inventory.getStackInSlot(0))) {
          return retStack;
          }	
        }else if(mainItem instanceof String){
        	String dict = (String) mainItem;
        	ItemStack result = inventory.getStackInSlot(0);
        	NonNullList<ItemStack> ore =OreDictionary.getOres(dict);
        	if(ore.isEmpty()) return retStack;
        	if (!OreDictionary.containsMatch(true, ore, result)) return retStack;
        }
        if (this.enchantment) {
            if (EnchantmentHelper.getEnchantments(inventory.getStackInSlot(0)).size() > 0) {
                return retStack;
            }
        }

        ArrayList<ItemStack> inventoryList = new ArrayList<ItemStack>();
        for (int i = 1; i < 9; i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                inventoryList.add(inventory.getStackInSlot(i).copy());
            }
        }

        if (subItems.isEmpty() && !inventoryList.isEmpty()) {
            return retStack;
        }
        
        if (!subItems.isEmpty() && inventoryList.size() != subItems.size()) {
            return retStack;
        }

        boolean flg1 = true;
        for (Object obj1 : subItems) {
            boolean flg2 = false;
            for (int i = 0; i < inventoryList.size(); i++) {
            	if(obj1 instanceof ItemStack){
            		ItemStack stack1 = (ItemStack) obj1;
	                if (ItemStack.areItemsEqual(stack1, inventoryList.get(i))) {
	                    inventoryList.remove(i);
	                    flg2 = true;
	                    break;
	                }
                }else if(obj1 instanceof String){
                	String dict = (String) obj1;
                	NonNullList<ItemStack> ore =OreDictionary.getOres(dict);
                	if (!ore.isEmpty()&&RecipesUtil.containsMatch(false, ore, inventoryList.get(i))) {
                        inventoryList.remove(obj1);
                        flg2 = true;
                        break;
                    }
                }
            }

            if (!flg2) {
                flg1 = false;
                break;
            }
        }

        if (!flg1) {
            return retStack;
        }

        return resultItem.copy();

    }

    public static void addPotRecipe(PotRecipes recipes) {
        potRecipesList.add(recipes);
    }




}