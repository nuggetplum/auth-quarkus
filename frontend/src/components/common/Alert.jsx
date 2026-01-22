import React from "react";
import { Alert as MuiAlert, AlertTitle } from "@mui/material";

const Alert = ({ severity, title, message, onClose, sx }) => {
  return (
    <MuiAlert severity={severity} onClose={onClose} sx={{ mb: 2, ...sx }}>
      {title && <AlertTitle>{title}</AlertTitle>}
      {message}
    </MuiAlert>
  );
};

export default Alert;
