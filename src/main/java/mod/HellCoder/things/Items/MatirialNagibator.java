    package mod.HellCoder.things.Items;

    import mod.HellCoder.things.core.MainHelper;
    import mod.HellCoder.things.lib.RegItems;
    import net.minecraft.block.Block;
    import net.minecraft.init.Blocks;
    import net.minecraft.init.Items;
    import net.minecraft.item.Item;

    public enum MatirialNagibator
    {
        NAGIBATOR(3, 3765, 228.0F, 15.9F, 1);
        /**
         * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = WOOD/GOLD)
         */
        private final int harvestLevel;
        /**
         * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
         */
        private final int maxUses;
        /**
         * The strength of this tool material against blocks which it is effective against.
         */
        private final float efficiencyOnProperMaterial;
        /** Damage versus entities. */
        private final float damageVsEntity;
        /** Defines the natural enchantability factor of the material. */
        private final int enchantability;

        private static final String __OBFID = "CL_00000042";

        //Added by forge for custom Tool materials.
        public Item customCraftingMaterial = null;

        private MatirialNagibator(int par3, int par4, float par5, float par6, int par7)
        {
            this.harvestLevel = par3;
            this.maxUses = par4;
            this.efficiencyOnProperMaterial = par5;
            this.damageVsEntity = par6;
            this.enchantability = par7;
        }

        /**
         * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
         */
        public int getMaxUses()
        {
            return this.maxUses;
        }

        /**
         * The strength of this tool material against blocks which it is effective against.
         */
        public float getEfficiencyOnProperMaterial()
        {
            return this.efficiencyOnProperMaterial;
        }

        /**
         * Damage versus entities.
         */
        public float getDamageVsEntity()
        {
            return this.damageVsEntity;
        }

        /**
         * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
         */
        public int getHarvestLevel()
        {
            return this.harvestLevel;
        }

        /**
         * Return the natural enchantability factor of the material.
         */
        public int getEnchantability()
        {
            return this.enchantability;
        }

        public Item func_150995_f()
        {
            switch (this)
            {
                case NAGIBATOR:    return RegItems.diga;
                default:      return customCraftingMaterial;
            }
        }
    }

