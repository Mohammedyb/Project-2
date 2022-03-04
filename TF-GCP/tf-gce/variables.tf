variable "project" {}
variable "instance_name_base" {
  description = "base name to apply to create instance"
  type        = string
  default     = "terraformed-instance-"
}

variable "region" {
  default = "us-central1"
}

variable "zone" {
  default = "us-central1-a"
}

variable "machine_type" {
  description = "GCE machine type family for the deployed resource"
  type        = string
  default     = "e2-medium"
}

variable "instance_tags" {
  description = "String array of tags to apply to the instance"
  default     = null
  type        = list(string)

}

variable "boot_image" {
  description = "GCE boot image"
  default     = "ubuntu-1804-lts"
  type        = string
}

variable "include_ssh_access" {
  type    = bool
  default = false
}

variable "ssh-key_file" {
  description = "path to ssh key file"
  type        = string
  default     = null
}

variable "ssh_username" {
  description = "Username to apply to ssh"
  default     = null
}

variable "credentials_file" {
  description = "Path to credentials file for service account used to create resources"
  type        = string
}

variable "vpc_name" {
  description = "Name to apply to create vpc resource"
  type        = string
  default     = "terraformed-network-"
}

variable "include_metadatascript" {
  type    = bool
  default = false
}

variable "metadata_script" {
  description = "Path to metadata startup script"
  type        = string
  default     = ""
}


variable "firewall_rules" {
  type        = map(any)
  description = "Map of all firewall rules to create"
  default = {
    "http-8080-ingress" = {
      source_ranges = ["0.0.0.0/0"]
      target_tags   = ["javasre"]
      allow = {
        protocol = "tcp"
        ports    = ["8080"]
      }
    },
    "ssh-ingress" = {
      source_ranges = ["0.0.0.0/0"]
      target_tags   = ["javasre"]
      allow = {
        protocol = "tcp"
        ports    = ["22"]
      }
    }
  }
}