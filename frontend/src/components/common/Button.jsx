import React from "react";
import { Button as MuiButton, CircularProgress } from "@mui/material";

const Button = ({
  children,
  loading,
  disabled,
  variant = "contained",
  color = "primary",
  ...props
}) => {
  return (
    <MuiButton
      variant={variant}
      color={color}
      disabled={disabled || loading}
      fullWidth
      {...props}
    >
      {loading ? <CircularProgress size={24} color="inherit" /> : children}
    </MuiButton>
  );
};

export default Button;
