import React from "react";
import { useNavigate } from "react-router-dom";
import { Box, Card, CardContent, Typography, Container } from "@mui/material";
import { useAuth } from "../contexts/AuthContext";
import Button from "../components/common/Button";

const DashboardPage = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <Container maxWidth="md">
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minHeight="100vh"
        sx={{ py: 4 }}
      >
        <Card sx={{ width: "100%" }}>
          <CardContent sx={{ p: 4 }}>
            <Typography
              variant="h4"
              component="h1"
              gutterBottom
              color="primary"
            >
              Dashboard
            </Typography>
            <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>
              Welcome back!
            </Typography>
            <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
              Email: {user?.email || "N/A"}
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ mb: 4 }}>
              You have successfully logged in to your account. This is a
              protected page that only authenticated users can access.
            </Typography>
            <Button onClick={handleLogout} color="secondary" sx={{ py: 1.5 }}>
              Logout
            </Button>
          </CardContent>
        </Card>
      </Box>
    </Container>
  );
};

export default DashboardPage;
