# ---- Builder Stage ----
# This stage installs all build tools and compiles dependencies
FROM python:3.11-slim as builder

# Install Rust toolchain in case it's needed for compilation
RUN curl https://sh.rustup.rs -sSf | sh -s -- -y
# Add cargo to path
ENV PATH="/root/.cargo/bin:${PATH}"

# Set the working directory
WORKDIR /app

# Install Python dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt


# ---- Final Stage ----
# This stage takes the pre-built packages and creates a slim final image
FROM python:3.11-slim

# Set the working directory
WORKDIR /app

# Copy the installed packages from the builder stage. This is the crucial step.
COPY --from=builder /usr/local/lib/python3.11/site-packages /usr/local/lib/python3.11/site-packages

# Copy the application code
COPY . .

# Command to run the application using Gunicorn for production
# -w 1: Use 1 worker process to conserve memory on Render's free tier
# -k uvicorn.workers.UvicornWorker: Use Uvicorn for async handling
# --log-level debug: Provide verbose logs for debugging
CMD ["gunicorn", "-w", "1", "-k", "uvicorn.workers.UvicornWorker", "app.main:app", "--bind", "0.0.0.0:10000", "--log-level", "debug"] 