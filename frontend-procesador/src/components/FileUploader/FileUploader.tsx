import React, { useState, FormEvent, useCallback } from "react";
import { uploadFile } from "../../api/uploadApi";
import { useDropzone } from "react-dropzone";
import Swal from "sweetalert2";
import "./FileUploader.css";

export const FileUploader: React.FC = () => {
    const [file, setFile] = useState<File | null>(null);
    const [fields, setFields] = useState({
        fileSize: "",
        observation: "",
        documentName: "",
        dailyNote: "",
        uploadDate: "",
    });
    const [status, setStatus] = useState<"idle" | "success" | "error" | "uploading">("idle");

    // Función para formatear el tamaño del archivo en KB
    const formatFileSize = (bytes: number): string => {
        return Math.round(bytes / 1024).toString(); // Convertir a KB
    };

    // Función para obtener la fecha actual en formato ISO
    const getCurrentDate = (): string => {
        return new Date().toISOString().slice(0, 19);
    };

    // Función para manejar los archivos cuando son arrastrados
    const onDrop = useCallback((acceptedFiles: File[]) => {
        if (acceptedFiles && acceptedFiles.length > 0) {
            const selectedFile = acceptedFiles[0];
            setFile(selectedFile);

            // Actualizar campos automáticamente
            setFields(prev => ({
                ...prev,
                documentName: selectedFile.name.replace(/\.[^/.]+$/, ""), // Eliminar extensión
                fileSize: formatFileSize(selectedFile.size),
                uploadDate: getCurrentDate()
            }));
        }
    }, []);

    const { getRootProps, getInputProps, isDragActive } = useDropzone({
        onDrop,
        accept: {
            'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet': ['.xlsx'],
        },
        maxFiles: 1
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFields(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();

        if (!file) {
            Swal.fire({
                icon: 'warning',
                title: 'Archivo requerido',
                text: 'Por favor selecciona un archivo Excel para subir.',
                confirmButtonColor: '#0087F7'
            });
            return;
        }

        // Mostrar loading
        Swal.fire({
            title: 'Subiendo archivo...',
            html: 'Por favor espera mientras se procesa tu archivo.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        setStatus("uploading");

        const formData = new FormData();
        formData.append("file", file);
        formData.append("fileSize", fields.fileSize);
        formData.append("observation", fields.observation);
        // Still include document name even though we removed the input field
        formData.append("documentName", fields.documentName || file.name.replace(/\.[^/.]+$/, ""));
        formData.append("dailyNote", fields.dailyNote);
        formData.append("uploadDate", fields.uploadDate);

        try {
            const response = await uploadFile(formData);
            if (response.ok) {
                setStatus("success");
                // Resetear el formulario después de una carga exitosa
                setFile(null);
                setFields({
                    fileSize: "",
                    observation: "",
                    documentName: "",
                    dailyNote: "",
                    uploadDate: "",
                });

                // Mostrar mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: '¡Archivo subido exitosamente!',
                    text: 'Tu archivo Excel ha sido procesado correctamente.',
                    confirmButtonColor: '#00b300'
                });
            } else {
                setStatus("error");
                const data = await response.json();
                const errorMsg = data.error || "Error en la carga.";
                
                // Mostrar mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error al subir archivo',
                    text: errorMsg,
                    confirmButtonColor: '#d33'
                });
            }
        } catch (err) {
            setStatus("error");
            const errorMsg = "Error de Conexión";
            
            // Mostrar mensaje de error
            Swal.fire({
                icon: 'error',
                title: 'Error de conexión',
                text: errorMsg,
                confirmButtonColor: '#d33'
            });
        }
    };

    return (
        <div className="file-uploader">
            <div className="uploader-header">
                <h2>Subir archivo Excel</h2>
                <p className="subtitle">Carga tus archivos Excel</p>
            </div>

            <form onSubmit={handleSubmit}>
                <div {...getRootProps()} className={`dropzone ${isDragActive ? 'active' : ''} ${file ? 'has-file' : ''}`}>
                    <input {...getInputProps()} />
                    <div className="dropzone-content">
                        <div className="dropzone-icon"></div>
                        {
                            file ? (
                                <div className="file-info">
                                    <p className="file-meta-info">
                                        Tamaño: {fields.fileSize} KB 
                                        <span className="meta-separator">•</span> 
                                       Fecha: {fields.uploadDate.substring(0, 10)}
                                    </p>
                                    Nombre de Archivo:<p className="file-selected">{file.name}</p>
                                </div>
                            ) : (
                                <div className="dropzone-text">
                                    <p className="dropzone-primary">Arrastra y suelta un archivo Excel aquí</p>
                                    <p className="dropzone-secondary">o haz clic para seleccionar un archivo</p>
                                    <div className="file-formats">Formatos aceptados: .xlsx</div>
                                </div>
                            )
                        }
                        {file && (
                            <button
                                type="button"
                                className="remove-file"
                                onClick={(e) => {
                                    e.stopPropagation();
                                    setFile(null);
                                    setFields(prev => ({
                                        ...prev,
                                        documentName: "",
                                        fileSize: "",
                                        uploadDate: ""
                                    }));
                                }}
                            >
                                ✖
                            </button>
                        )}
                    </div>
                </div>

                <div className="form-grid">
                <div className="form-field full-width">
                    <label htmlFor="observation">
                        Observación:
                    </label>
                    <textarea
                        id="observation"
                        name="observation"
                        placeholder="Ingresa una observación detallada sobre este archivo"
                        value={fields.observation}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="form-field full-width">
                    <label htmlFor="dailyNote">
                        Nota diaria:
                    </label>
                    <textarea
                        id="dailyNote"
                        name="dailyNote"
                        placeholder="Agrega una nota diaria relacionada con este archivo"
                        value={fields.dailyNote}
                        onChange={handleChange}
                        required
                    />
                </div>
                </div> {/* Cierre de form-grid */}

                <button
                    type="submit"
                    className={`submit-button ${!file ? 'disabled' : ''}`}
                    disabled={status === "uploading" || !file}
                >
                    {status === "uploading" ?
                        <>Procesando...</> :
                        <>Subir archivo</>
                    }
                </button>
            </form>
        </div>
    );
};

export default FileUploader;
