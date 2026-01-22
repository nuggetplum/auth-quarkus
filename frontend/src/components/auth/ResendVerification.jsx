import React, { useState } from "react";
import { useFormik } from "formik";
import { useNavigate, Link } from "react-router-dom";
import {
  Box,
  Card,
  CardContent,
  Typography,
  Link as MuiLink,
} from "@mui/material";
import { toast } from "react-toastify";
import { useAuth } from "../../contexts/AuthContext";
import { resendVerificationSchema } from "../../utils/validators";
import InputField from "../common/InputField";
import Button from "../common/Button";
import Alert from "../common/Alert";

const ResendVerification = () => {
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const { resendVerification } = useAuth();
  const navigate = useNavigate();

  const formik = useFormik({
    initialValues: {
      email: "",
    },
    validationSchema: resendVerificationSchema,
    onSubmit: async (values) => {
      setLoading(true);
      try {
        await resendVerification(values.email);
        setSuccess(true);
        toast.success("Verification email sent! Please check your inbox.");
      } catch (error) {
        toast.error(error.message || "Failed to resend verification email.");
      } finally {
        setLoading(false);
      }
    },
  });

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
      sx={{ bgcolor: "background.default", p: 2 }}
    >
      <Card sx={{ maxWidth: 450, width: "100%" }}>
        <CardContent sx={{ p: 4 }}>
          <Typography
            variant="h4"
            component="h1"
            gutterBottom
            align="center"
            color="primary"
          >
            Resend Verification
          </Typography>
          <Typography
            variant="body2"
            color="text.secondary"
            align="center"
            sx={{ mb: 3 }}
          >
            Enter your email to receive a new verification link
          </Typography>

          {success && (
            <Alert
              severity="success"
              message="Verification email sent! Please check your inbox and spam folder."
            />
          )}

          <form onSubmit={formik.handleSubmit}>
            <InputField
              label="Email"
              name="email"
              type="email"
              value={formik.values.email}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.email && Boolean(formik.errors.email)}
              helperText={formik.touched.email && formik.errors.email}
            />

            <Button
              type="submit"
              loading={loading}
              sx={{ mt: 3, mb: 2, py: 1.5 }}
            >
              Resend Verification Email
            </Button>

            <Typography variant="body2" align="center">
              Remember your credentials?{" "}
              <MuiLink component={Link} to="/login" underline="hover">
                Login here
              </MuiLink>
            </Typography>
          </form>
        </CardContent>
      </Card>
    </Box>
  );
};

export default ResendVerification;
