import'./MedicineDetails.css';

function MedicineDetails(props) {
  const { medicine, onDelete, onEdit, onCalculate } = props;

  return (
    <section className="selected-medicine">
      <div className="medicine-container">
        <div className="medicine-card">
          <div className="card-header">
            <h2>{medicine.name}</h2>
            <div className="button-container">
              <button className="btn-secondary" onClick={onEdit}>Edit</button>
              <button className="btn-primary" onClick={onCalculate}>Calculate</button>
              <button className="btn-danger" onClick={() => onDelete(medicine.id)}>Delete</button>
            </div>
          </div>
          <p className="details">{medicine.details}</p>
          <div className="dosage-info">
            <p>Minimum dose: {medicine.dosage?.minimum_factor ?? 'N/A'} mg</p>
            <p>Maximum dose: {medicine.dosage?.maximum_factor ?? 'N/A'} mg</p>
            <p>Frequency: {medicine.dosage?.dosage_frequency ?? 'N/A'}</p>
            <p>Maximum daily dose: {medicine.dosage?.max_daily_dose ?? 'N/A'} mg</p>
            <p>Average weight: {medicine.dosage?.avg_weight ?? 'N/A'} kg</p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default MedicineDetails;