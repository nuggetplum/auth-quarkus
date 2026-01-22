import React, { useState, useEffect } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import {
  Box,
  Card,
  CardContent,
  Typography,
  CircularProgress,
} from "@mui/material";
import { CheckCircle, Error } from "@mui/icons-material";
import { toast } from "react-toastify";
import { useAuth } from "../../contexts/AuthContext";
import Button from "../common/Button";

const EmailVerification = () => {
  const [searchParams] = useSearchParams();
  const [status, setStatus] = useState("verifying"); // verifying, success, error
  const [message, setMessage] = useState("");
  const { verifyEmail } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const token = searchParams.get("token");

    if (!token) {
      setStatus("error");
      setMessage("Invalid verification link");
      return;
    }

    const verify = async () => {
      try {
        const response = await verifyEmail(token);
        setStatus("success");
        setMessage(response.message || "Email verified successfully!");
        toast.success("Email verified! You can now login.");
      } catch (error) {
        setStatus("error");
        setMessage(
          error.message || "Verification failed. The link may be expired.",
        );
        toast.error("Verification failed");
      }
    };

    verify();
  }, [searchParams, verifyEmail]);

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
      sx={{ bgcolor: "background.default", p: 2 }}
    >
      <Card sx={{ maxWidth: 450, width: "100%" }}>
        <CardContent sx={{ p: 4, textAlign: "center" }}>
          {status === "verifying" && (
            <>
              <CircularProgress size={60} sx={{ mb: 3 }} />
              <Typography variant="h5" gutterBottom>
                Verifying your email...
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Please wait while we verify your email address.
              </Typography>
            </>
          )}

          {status === "success" && (
            <>
              <CheckCircle
                sx={{ fontSize: 60, color: "success.main", mb: 3 }}
              />
              <Typography variant="h5" gutterBottom color="success.main">
                Email Verified!
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
                {message}
              </Typography>
              <Button onClick={() => navigate("/login")}>Go to Login</Button>
            </>
          )}

          {status === "error" && (
            <>
              <Error sx={{ fontSize: 60, color: "error.main", mb: 3 }} />
              <Typography variant="h5" gutterBottom color="error.main">
                Verification Failed
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
                {message}
              </Typography>
              <Button onClick={() => navigate("/resend-verification")}>
                Resend Verification Email
              </Button>
            </>
          )}
        </CardContent>
      </Card>
    </Box>
  );
};

export default EmailVerification;
