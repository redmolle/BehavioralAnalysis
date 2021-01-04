import React, { useState, useEffect } from "react";

const useForm = (initialFieldValues, validate, setCurrentId=undefined) => {
    const [values, setValues] = useState(initialFieldValues);
    const [errors, setErrors] = useState({});

    const handleInputChange = e => {
        const { name, value } = e.target;
        const fieldValue = { [name]: value };
        setValues({
            ...values,
            ...fieldValue
        });
        validate(fieldValue);
    };

    const resetForm = () => {
        setValues({
            ...initialFieldValues
        });
        setErrors({});
        if (setCurrentId) {
            setCurrentId(0);
        }
    };

    return {
        values,
        setValues,
        errors,
        setErrors,
        handleInputChange,
        resetForm
    };
};

export default useForm;