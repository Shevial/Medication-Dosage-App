import { useState } from 'react';
import'./MedicineForm.css';

function MedicineForm({ medicine, onSubmit, onCancel }) {
  const [formData, setFormData] = useState({
    name: medicine?.name || '',
    details: medicine?.details || '',
    dosage: {
      minimum_factor: medicine?.dosage?.minimum_factor || '',
      maximum_factor: medicine?.dosage?.maximum_factor || '',
      dosage_frequency: medicine?.dosage?.dosage_frequency || '',
      max_daily_dose: medicine?.dosage?.max_daily_dose || '',
      avg_weight: medicine?.dosage?.avg_weight || ''
    }
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name.startsWith('dosage.')) {
      const dosageField = name.split('.')[1];
      setFormData(prev => ({
        ...prev,
        dosage: {
          ...prev.dosage,
          [dosageField]: value
        }
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      ...medicine,
      ...formData
    });
  };

  return (
    <form onSubmit={handleSubmit} className="medicine-form">
      <label>
        Name:
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
        />
      </label>

      <label>
        Details:
        <textarea
          name="details"
          value={formData.details}
          onChange={handleChange}
        />
      </label>

      <div className="dosage-fields">
        <h3>Dose</h3>
        <label>
          Minimum dose: (in mg)
          <input
            type="number"
            name="dosage.minimum_factor"
            value={formData.dosage.minimum_factor}
            onChange={handleChange}
          />
        </label>
        <label>
          Maximum dose: (in mg)
          <input
            type="number"
            name="dosage.maximum_factor"
            value={formData.dosage.maximum_factor}
            onChange={handleChange}
          />
        </label>
        <label>
          Frequency:
          <input
            type="text"
            name="dosage.dosage_frequency"
            value={formData.dosage.dosage_frequency}
            onChange={handleChange}
          />
          </label>
          <label>
          Maximum daily dose:
          <input
            type="number"
            name="dosage.max_daily_dose"
            value={formData.dosage.max_daily_dose}
            onChange={handleChange}
          />
          </label>
          <label>
          Average Weight:
          <input
            type="number"
            name="dosage.avg_weight"
            value={formData.dosage.avg_weight}
            onChange={handleChange}
          />
          </label>
      </div>

      <div className="form-buttons">
        <button type="submit" className="btn-primary">Save</button>
        <button type="button" className="btn-secondary" onClick={onCancel}>Cancel</button>
      </div>
    </form>
  );
}

export default MedicineForm;