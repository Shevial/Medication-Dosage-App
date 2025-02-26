import { useState } from 'react';
import './CalculationForm.css';
function CalculationForm({ onSubmit, onCancel }) {
  const [formData, setFormData] = useState({
    age: '',
    weight: ''
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="medicine-form">
      <h3>Patient data</h3>
      
      <label>
        Age (years):
        <input
          type="number"
          name="age"
          value={formData.age}
          onChange={handleChange}
          required
          min="0"
        />
      </label>

      <label>
        Weight (kg):
        <input
          type="number"
          name="weight"
          value={formData.weight}
          onChange={handleChange}
          required
          min="0"
        />
      </label>

      <div className="form-buttons">
        <button type="submit"  className="btn-primary">Calculate</button>
        <button type="button"  className="btn-secondary" onClick={onCancel}>Cancel</button>
      </div>
    </form>
  );
}

export default CalculationForm;