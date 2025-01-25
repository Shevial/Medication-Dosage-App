La clase medicine contiene 

    @ManyToOne
    @JoinColumn(name = "dosage_medication_id")
    private Dosage dosage;

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }
Para realizar bien la relacion entre tablas a la hora de modificar el contenido de la base de datos