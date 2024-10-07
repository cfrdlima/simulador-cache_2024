//package simulador.cache;
//
//public enum Associatividade {
//    DIRETA, CONJUNTO, TOTAL;
//
//    public static Associatividade getAssociatividade(Cache cache) {
//        int numeroConjuntos = cache.getConjuntos().length;
//        int numeroBloco = cache.getConjuntos()[0].getBlocos().length;
//        if (numeroConjuntos == 1) {
//            return DIRETA;
//        } else if (numeroConjuntos == numeroBloco) {
//            return CONJUNTO;
//        } else {
//            return TOTAL;
//        }
//    }
//}
