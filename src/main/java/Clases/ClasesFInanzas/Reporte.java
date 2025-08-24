package Clases.ClasesFInanzas;

public class Reporte {
    private int totalProductosVendidos;
    private String clienteMasFrecuente;
    private double totalVentasMes;
    private String platoMasVendido;

    public Reporte(int totalProductosVendidos, String clienteMasFrecuente, double totalVentasMes, String platoMasVendido) {
        this.totalProductosVendidos = totalProductosVendidos;
        this.clienteMasFrecuente = clienteMasFrecuente;
        this.totalVentasMes = totalVentasMes;
        this.platoMasVendido = platoMasVendido;
    }

    public int getTotalProductosVendidos() {
        return totalProductosVendidos;
    }

    public void setTotalProductosVendidos(int totalProductosVendidos) {
        this.totalProductosVendidos = totalProductosVendidos;
    }

    public String getClienteMasFrecuente() {
        return clienteMasFrecuente;
    }

    public void setClienteMasFrecuente(String clienteMasFrecuente) {
        this.clienteMasFrecuente = clienteMasFrecuente;
    }

    public double getTotalVentasMes() {
        return totalVentasMes;
    }

    public void setTotalVentasMes(double totalVentasMes) {
        this.totalVentasMes = totalVentasMes;
    }

    public String getPlatoMasVendido() {
        return platoMasVendido;
    }

    public void setPlatoMasVendido(String platoMasVendido) {
        this.platoMasVendido = platoMasVendido;
    }
}
