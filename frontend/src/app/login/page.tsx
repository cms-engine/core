"use client";

import { useState } from "react";
import { TextField, Button, Checkbox, FormControlLabel, Container, Typography, Paper, Alert } from "@mui/material";
import React from "react";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [rememberMe, setRememberMe] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    try {
      const response = await fetch("/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({
          username,
          password,
          "remember-me": rememberMe.toString(),
        }).toString(),
      });

      // Handle response based on status
      if (response.redirected) {
        const redirectedUrl = response.url; // Get the redirected URL
        const url = new URL(redirectedUrl);

        // Check if the URL has a query parameter
        const errorParam = url.searchParams.get("error");
        const continueParam = url.searchParams.get("continue");

        if (errorParam !== null) {
          setError("Invalid credentials. Please try again.");
        } else if (continueParam !== null) {
          // If there's a continue parameter, you can redirect or handle the success
          window.location.href = redirectedUrl; // Follow the redirect
        }
      } else {
        setError("Server error. Please try again later.");
      }
    } catch {
      setError("Server error. Please try again later.");
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <Paper elevation={3} sx={{ p: 4, mt: 8, textAlign: "center" }}>
        <Typography variant="h5" gutterBottom>
          Please Sign In
        </Typography>
        {error && <Alert severity="error">{error}</Alert>}
        <form onSubmit={handleSubmit}>
          <TextField
            fullWidth
            label="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            margin="normal"
          />
          <TextField
            fullWidth
            type="password"
            label="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            margin="normal"
          />
          <FormControlLabel
            control={<Checkbox checked={rememberMe} onChange={(e) => setRememberMe(e.target.checked)} />}
            label="Remember me"
          />
          <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
            Sign In
          </Button>
        </form>
      </Paper>
    </Container>
  );
}
