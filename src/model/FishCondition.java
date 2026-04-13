package model;

public enum FishCondition {
    ZDROWA{
        @Override
        public String toString() {
            return "zdrowa";
        }
    },
    CHORA{
        @Override
        public String toString() {
            return "chora";
        }
    },
    W_TRAKCIE_LECZENIA{
        @Override
        public String toString() {
            return "w trakcie leczenia";
        }
    },
    KWARANTANNA{
        @Override
        public String toString() {
            return "kwarantanna";
        }
    }
}
