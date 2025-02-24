import './App.css';
import { useState, useEffect } from 'react';
import MedicineDetails from './components/MedicineDetails';
import MedicineList from './components/MedicineList';
import MedicineForm from './components/MedicineForm';
import CalculationForm from './components/CalculationForm';
import Footer from './components/Footer'; 
import Banner from './components/Banner'; 


function App() {
  const [selectedMedicine, setSelectedMedicine] = useState(null);
  const [medicines, setMedicines] = useState([]);
  const [isEditing, setIsEditing] = useState(false);
  const [isCreating, setIsCreating] = useState(false); 
  const [isCalculating, setIsCalculating] = useState(false); 
  const [calculationResult, setCalculationResult] = useState(null);
  const [medicineForCalculation, setMedicineForCalculation] = useState(null);


  useEffect(() => {
    fetchMedicines();
  }, []);

  const fetchMedicines = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/medicines/view');
      const data = await response.json();
      setMedicines(data);
    } catch (error) {
      console.error("Error fetching medicines:", error);
    }
  };

  const handleDeleteMedicine = async (medicineId) => {
    try {
      await fetch(`http://localhost:8080/api/medicines/delete/${medicineId}`, {
        method: 'DELETE',
      });
      setMedicines(prev => prev.filter(med => med.id !== medicineId));
      if (selectedMedicine?.id === medicineId) setSelectedMedicine(null);
    } catch (error) {
      console.error("Error deleting medicine:", error);
    }
  };

  const handleCreateMedicine = async (newMedicine) => {
    try {
      const response = await fetch('http://localhost:8080/api/medicines/form', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newMedicine),
      });

      if (response.ok) {
        const createdMedicine = await response.json();
        setMedicines(prev => [...prev, createdMedicine]);
        setIsCreating(false);
      }
    } catch (error) {
      console.error("Error creating medicine:", error);
    }
  };

  const handleUpdateMedicine = async (updatedMedicine) => {
    try {
      const response = await fetch(`http://localhost:8080/api/medicines/form/${updatedMedicine.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedMedicine),
      });

      if (response.ok) {
        setMedicines(prev => 
          prev.map(med => med.id === updatedMedicine.id ? updatedMedicine : med)
        );
        setSelectedMedicine(updatedMedicine);
        setIsEditing(false);
      }
    } catch (error) {
      console.error("Error updating medicine:", error);
    }
  };

  const handleCalculateDosage = async (data) => {
    try {
      const params = new URLSearchParams({
        age: data.age,
        weight: data.weight,
        medicineId: medicineForCalculation.id
      });
      
      const response = await fetch(`http://localhost:8080/api/dosage/calculate?${params.toString()}`, {
        method: 'POST'
      });

      if (!response.ok) throw new Error(await response.text());
      
      const result = await response.json();
      setCalculationResult(result);
      setIsCalculating(false);
      
    } catch (error) {
      console.error("Error calculating dosage:", error);
      alert(error.message);
    }
  };

  return (
    <>
    <div className="app-container">

     <Banner />
     <main className="main-content">

      <div className="header-controls">
        <button 
          className="create-button"
          onClick={() => setIsCreating(true)}
        >
          Insert new medication
        </button>
      </div>

      {/* Modal para creación */}
      {isCreating && (
        <div className="modal-backdrop" onClick={() => setIsCreating(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>New medication</h2>
            <MedicineForm
              onSubmit={handleCreateMedicine}
              onCancel={() => setIsCreating(false)}
            />
          </div>
        </div>
      )}

      {/* Modal para edición */}
      {isEditing && (
        <div className="modal-backdrop" onClick={() => setIsEditing(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Edit medication</h2>
            <MedicineForm
              medicine={selectedMedicine}
              onSubmit={handleUpdateMedicine}
              onCancel={() => setIsEditing(false)}
            />
          </div>
        </div>
      )}

      {/* Modal para cálculo */}
      {isCalculating && (
       <div className="modal-backdrop" onClick={(e) => e.stopPropagation()}>
         <div className="modal-content">
            <CalculationForm
              onSubmit={handleCalculateDosage}
              onCancel={() => setIsCalculating(false)}
            />
          </div>
        </div>
      )}

      {/* Modal para resultados */}
      {calculationResult && (
        <div className="modal-backdrop" onClick={() => setCalculationResult(null)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Result</h2>
            <div className="calculation-result">
              <h3>{calculationResult.medicine.name}</h3>
              <p>Patient age: {calculationResult.patient.age}</p>
              <p>Patient weight: {calculationResult.patient.weight} kg</p>
              <div dangerouslySetInnerHTML={{ __html: calculationResult.dosageResult }} />
              <div className="form-buttons">
              <button className="btn-secondary" onClick={() => { 
                setCalculationResult(null);
                setMedicineForCalculation(null);
              }}>Close</button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Lista de medicamentos */}

      <MedicineList
        medicines={medicines}
        selectMedicine={(med) => {
          setSelectedMedicine(med);
          setIsEditing(false);
          setIsCreating(false);
        }}
        onDelete={handleDeleteMedicine}
      />

      {/* Detalles del medicamento seleccionado */}
      {selectedMedicine && !isEditing && (
        <div className = "modal-backdrop" onClick = {() => setSelectedMedicine(null)}>
          <div className = "modal-content" onClick = {(e) => e.stopPropagation()}>
            <MedicineDetails 
              medicine = {selectedMedicine}
              onDelete = {handleDeleteMedicine}
              onEdit = {() => setIsEditing(true)}
              onCalculate={() => {
                setMedicineForCalculation(selectedMedicine); 
                setIsCalculating(true);                      
                setSelectedMedicine(null);                  
              }}       
            />
          </div>
        </div>
      )}
        </main>
      <Footer/>
      </div>
    </>
  );
}

export default App;